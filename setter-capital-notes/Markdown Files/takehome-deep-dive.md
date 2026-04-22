# Take-Home Deep Dive — Setter Capital

---

## File Structure

```
project/
├── backend/
│   ├── data/
│   │   └── funds_data.json       ← the data store (acts as a database)
│   └── src/
│       └── index.ts              ← entire backend: server setup + all API routes
└── frontend/
    └── src/
        └── app/
            ├── services/
            │   └── funds.service.ts          ← all HTTP calls to the backend
            └── pages/
                ├── funds-table/
                │   ├── funds-table.component.ts    ← table logic
                │   ├── funds-table.component.html  ← table template
                │   └── funds-table.component.scss  ← table styles
                ├── fund-detail/
                │   ├── fund-detail.component.ts    ← detail view logic
                │   ├── fund-detail.component.html  ← detail template
                │   └── fund-detail.component.scss  ← detail styles
                └── fund-edit/
                    ├── fund-edit.component.ts      ← edit/delete logic
                    ├── fund-edit.component.html    ← edit template
                    └── fund-edit.component.scss    ← edit styles
```

---

## Backend — `src/index.ts`

### Server Setup
```typescript
import express, { Request, Response } from 'express';
import fs from 'fs';
import path from 'path';
import cors from 'cors';

const app = express();
const port = process.env.PORT || 3000;
const DATA_PATH = path.join(__dirname, '../data/funds_data.json');

app.use(cors());
app.use(express.json());
```

**What each line does:**
- `express()` — creates the Express application instance
- `process.env.PORT || 3000` — uses environment variable if set, otherwise defaults to 3000. Good practice for deployment
- `path.join(__dirname, '../data/funds_data.json')` — builds an absolute file path. `__dirname` is the directory of the compiled file (`dist/`), so `../data` goes up one level to `backend/data/`
- `app.use(cors())` — middleware that allows the Angular frontend (port 4300) to call this API (port 3000). Without this, the browser blocks cross-origin requests
- `app.use(express.json())` — middleware that parses incoming JSON request bodies. Without this, `req.body` would be undefined on PUT requests

---

### Helper Functions
```typescript
const readFunds = (): any[] => JSON.parse(fs.readFileSync(DATA_PATH, 'utf-8'));
const writeFunds = (data: any[]) => fs.writeFileSync(DATA_PATH, JSON.stringify(data, null, 2));
```

**What they do:**
- `readFunds()` — reads the JSON file from disk and parses it into a JavaScript array. Called on every GET request so it always returns the latest data
- `writeFunds()` — takes a JavaScript array, converts it to formatted JSON string, and writes it back to disk. The `null, 2` formats it with 2-space indentation so the file stays human-readable
- These act as a simple database — in production you would replace these with actual database queries

**Why synchronous (`readFileSync` / `writeFileSync`)?**
- Simpler for a small project with low traffic
- In production with high concurrency you'd use async versions or a real database to avoid blocking the event loop

---

### GET All Funds
```typescript
app.get('/api/funds', (req: Request, res: Response) => {
  res.json(readFunds());
});
```

**What it does:**
- Listens for GET requests to `/api/funds`
- Reads the JSON file and returns the entire array as a JSON response
- Called by the funds table on page load to populate all 100 rows

---

### GET Single Fund
```typescript
app.get('/api/funds/:index', (req: Request, res: Response) => {
  const funds = readFunds();
  const fund = funds[Number(req.params.index)];
  if (!fund) {
    res.status(404).json({ error: 'Not found' });
  } else {
    res.json({ ...fund, index: Number(req.params.index) });
  }
});
```

**What it does:**
- `:index` is a URL parameter — e.g. `/api/funds/3` gives `req.params.index = "3"`
- `Number()` converts the string param to a number for array indexing
- `funds[index]` accesses the fund at that position in the array
- `{ ...fund, index }` spreads the fund object and adds the index back — the frontend needs the index for navigation and edit/delete operations
- Returns `404` if the index doesn't exist

**Why use array index instead of an ID?**
- The data has no unique ID field
- Array index is the simplest identifier available
- Tradeoff: indices shift after a delete (e.g. deleting index 2 makes old index 3 become index 2). In production you'd add a proper unique ID to each record

---

### PUT Update Fund
```typescript
app.put('/api/funds/:index', (req: Request, res: Response) => {
  const funds = readFunds();
  const idx = Number(req.params.index);
  if (!funds[idx]) {
    res.status(404).json({ error: 'Not found' });
  } else {
    funds[idx] = { ...funds[idx], ...req.body };
    writeFunds(funds);
    res.json(funds[idx]);
  }
});
```

**What it does:**
- Reads the full array from disk
- Finds the fund at the given index
- `{ ...funds[idx], ...req.body }` — merges the existing fund with the incoming changes. Any fields in `req.body` overwrite the existing ones, any fields not in `req.body` are preserved
- Writes the updated array back to disk
- Returns the updated fund object

**Why spread both objects?**
- Partial updates — the frontend might only send changed fields
- Protects against accidentally wiping fields that weren't included in the request

---

### DELETE Fund
```typescript
app.delete('/api/funds/:index', (req: Request, res: Response) => {
  const funds = readFunds();
  const idx = Number(req.params.index);
  if (!funds[idx]) {
    res.status(404).json({ error: 'Not found' });
  } else {
    funds.splice(idx, 1);
    writeFunds(funds);
    res.json({ success: true });
  }
});
```

**What it does:**
- `splice(idx, 1)` removes exactly 1 element at position `idx` and shifts everything after it down
- Writes the modified array back to disk
- Returns `{ success: true }` to confirm deletion

---

## Frontend — `funds.service.ts`

```typescript
@Injectable({ providedIn: 'root' })
export class FundsService {
  private api = 'http://localhost:3000/api';

  constructor(private http: HttpClient) {}

  getAll(): Observable<Fund[]> {
    return this.http.get<Fund[]>(`${this.api}/funds`);
  }

  getOne(index: number): Observable<Fund> {
    return this.http.get<Fund>(`${this.api}/funds/${index}`);
  }

  update(index: number, fund: Partial<Fund>): Observable<Fund> {
    return this.http.put<Fund>(`${this.api}/funds/${index}`, fund);
  }

  delete(index: number): Observable<any> {
    return this.http.delete(`${this.api}/funds/${index}`);
  }
}
```

**What it does:**
- `@Injectable({ providedIn: 'root' })` — registers this service as a singleton available everywhere in the app
- `private api` — base URL stored once so if the backend URL changes you only update one place
- Each method maps to one backend route
- `Partial<Fund>` on update — means not all Fund fields are required, only the ones being updated
- Returns `Observable` — Angular's async data stream. Components subscribe to these to get the data

**Why a service instead of calling HTTP directly in components?**
- Single responsibility — components handle UI, service handles data
- Reusable — any component can inject and use it
- Easier to test and maintain

---

## Frontend — `funds-table.component.ts`

### Key Properties
```typescript
allFunds: (Fund & { index: number })[] = [];
filteredFunds: (Fund & { index: number })[] = [];
pagedFunds: (Fund & { index: number })[] = [];
```

**Three separate arrays — why?**
- `allFunds` — the full dataset from the API, never mutated
- `filteredFunds` — result of search + year filter + sort applied to allFunds
- `pagedFunds` — the current page slice of filteredFunds shown in the table
- Keeping them separate means filtering never loses the original data

---

### `ngOnInit()`
```typescript
ngOnInit() {
  this.fundsService.getAll().subscribe({
    next: (data) => {
      this.allFunds = data.map((f, i) => ({ ...f, index: i }));
      this.availableYears = [...new Set(this.allFunds.map(f => f.vintage))].sort((a, b) => b - a);
      this.applyFilters();
      this.loading = false;
    },
    error: () => this.loading = false
  });
}
```

**What it does:**
- `ngOnInit` is a lifecycle hook — runs once after the component initializes
- `.subscribe()` kicks off the HTTP request and handles the response
- `data.map((f, i) => ({ ...f, index: i }))` — adds the array index to each fund object so we can use it for navigation and API calls later
- `new Set(...)` — removes duplicate vintage years, then spread into array and sort descending for the year filter pills
- `applyFilters()` — runs immediately so the table renders with default sort applied

---

### `applyFilters()`
```typescript
applyFilters() {
  const q = this.searchQuery.toLowerCase().trim();

  let result = this.allFunds.filter(f => {
    const matchesSearch = !q ||
      f.name.toLowerCase().includes(q) ||
      f.strategies.some(s => s.toLowerCase().includes(q)) ||
      f.geographies.some(g => g.toLowerCase().includes(q)) ||
      f.currency.toLowerCase().includes(q) ||
      f.managers.some(m => m.toLowerCase().includes(q));

    const matchesYear = !this.selectedYear || f.vintage === this.selectedYear;

    return matchesSearch && matchesYear;
  });

  result.sort((a, b) => {
    let valA: string | number = a[this.sortField];
    let valB: string | number = b[this.sortField];
    if (typeof valA === 'string') valA = valA.toLowerCase();
    if (typeof valB === 'string') valB = valB.toLowerCase();
    if (valA < valB) return this.sortDir === 'asc' ? -1 : 1;
    if (valA > valB) return this.sortDir === 'asc' ? 1 : -1;
    return 0;
  });

  this.filteredFunds = result;
  this.totalPages = Math.ceil(this.filteredFunds.length / this.pageSize);
  this.currentPage = 1;
  this.updatePage();
}
```

**What it does step by step:**
1. Converts search query to lowercase for case-insensitive matching
2. Filters `allFunds` — a fund passes if it matches BOTH search AND year filter
3. `!q` — if no search query, all funds pass the search check
4. `.some()` on arrays — returns true if at least one element matches
5. Sort — compares two funds on the active sort field. Returns -1, 0, or 1 to determine order. Flips sign based on `sortDir`
6. Resets to page 1 whenever filters change so you don't end up on a non-existent page
7. Calls `updatePage()` to slice the result for the current page

---

### `setSort()`
```typescript
setSort(field: SortField) {
  if (this.sortField === field) {
    this.sortDir = this.sortDir === 'asc' ? 'desc' : 'asc';
  } else {
    this.sortField = field;
    this.sortDir = 'asc';
  }
  this.applyFilters();
}
```

**What it does:**
- If clicking the same column — toggle direction (asc → desc → asc)
- If clicking a different column — switch to that column and reset to ascending
- Then re-runs `applyFilters()` which re-sorts and re-paginates

---

### `updatePage()`
```typescript
updatePage() {
  const start = (this.currentPage - 1) * this.pageSize;
  this.pagedFunds = this.filteredFunds.slice(start, start + this.pageSize);
}
```

**What it does:**
- Calculates the start index for the current page
- Page 1: slice(0, 15), Page 2: slice(15, 30), Page 3: slice(30, 45) etc.
- `slice()` never mutates the original array — returns a new array

---

## Frontend — `fund-edit.component.ts`

### Array Fields as Strings
```typescript
strategiesStr = '';
geographiesStr = '';
managersStr = '';
```

**Why store arrays as comma-separated strings?**
- HTML inputs work with strings, not arrays
- Easier for the admin to edit — "Venture Capital, Hedge Fund" is clearer than a dynamic list of inputs
- Converted back to arrays on save

---

### `ngOnInit()`
```typescript
ngOnInit() {
  this.index = Number(this.route.snapshot.paramMap.get('index'));
  this.fundsService.getOne(this.index).subscribe({
    next: (data) => {
      this.fund = data;
      this.strategiesStr = data.strategies.join(', ');
      this.geographiesStr = data.geographies.join(', ');
      this.managersStr = data.managers.join(', ');
      this.loading = false;
    }
  });
}
```

**What it does:**
- `route.snapshot.paramMap.get('index')` — reads the `:index` from the URL
- `.join(', ')` — converts array to comma-separated string for the input field
- e.g. `["Venture Capital", "Hedge Fund"]` → `"Venture Capital, Hedge Fund"`

---

### `autoSave()`
```typescript
autoSave() {
  if (!this.fund) return;
  this.saveStatus = 'saving';
  const payload = {
    ...this.fund,
    strategies: this.strategiesStr.split(',').map(s => s.trim()).filter(Boolean),
    geographies: this.geographiesStr.split(',').map(s => s.trim()).filter(Boolean),
    managers: this.managersStr.split(',').map(s => s.trim()).filter(Boolean),
  };
  this.fundsService.update(this.index, payload).subscribe({
    next: () => {
      this.saveStatus = 'saved';
      setTimeout(() => this.saveStatus = 'idle', 2000);
    },
    error: () => this.saveStatus = 'error'
  });
}
```

**What it does step by step:**
1. Early return if `fund` is undefined — prevents errors during loading
2. Sets `saveStatus = 'saving'` — shows "⏳ Saving..." in the UI
3. Builds the payload — spreads current fund data and converts string fields back to arrays
4. `.split(',')` — splits "Venture Capital, Hedge Fund" back into `["Venture Capital", " Hedge Fund"]`
5. `.map(s => s.trim())` — removes whitespace from each item
6. `.filter(Boolean)` — removes empty strings in case of trailing commas
7. Calls `update()` on the service — fires the PUT request
8. On success — sets status to 'saved', then resets to 'idle' after 2 seconds
9. On error — shows error state in UI

**Why `blur` and not `input` or `change`?**
- `input` fires on every keystroke — would make an API call for every character typed, very wasteful
- `change` fires when the field loses focus AND value changed — similar to blur but less reliable cross-browser
- `blur` fires when the field loses focus — natural save point, user has finished typing

---

### Delete Flow
```typescript
confirmDelete() { this.showDeleteConfirm = true; }
cancelDelete() { this.showDeleteConfirm = false; }

deleteFund() {
  this.fundsService.delete(this.index).subscribe({
    next: () => this.router.navigate(['/funds'])
  });
}
```

**What it does:**
- Two-step delete — `confirmDelete()` shows the modal, `deleteFund()` actually deletes
- Prevents accidental deletion — user has to explicitly confirm
- On successful delete — navigates back to the table since the record no longer exists

---

## app.routes.ts

```typescript
export const routes: Routes = [
  { path: '', redirectTo: 'funds', pathMatch: 'full' },
  { path: 'funds', component: FundsTableComponent },
  { path: 'funds/:index/detail', component: FundDetailComponent },
  { path: 'funds/:index/edit', component: FundEditComponent },
  { path: '**', redirectTo: 'funds' }
];
```

**What each route does:**
- `''` — empty path redirects to `/funds`. `pathMatch: 'full'` means only exact empty string, not all paths
- `funds` — the main table view
- `funds/:index/detail` — detail view for a specific fund. `:index` is dynamic e.g. `/funds/3/detail`
- `funds/:index/edit` — edit view for a specific fund
- `**` — wildcard, catches any unmatched URL and redirects to funds. Acts as a 404 handler

---

## Fund Interface — `funds.service.ts`

```typescript
export interface Fund {
  index?: number;
  name: string;
  strategies: string[];
  geographies: string[];
  currency: string;
  fundSize: number;
  vintage: number;
  managers: string[];
  description: string;
}
```

**What each field represents:**
- `index?` — optional because the raw JSON doesn't have it, we add it ourselves
- `strategies: string[]` — array because a fund can have multiple strategies
- `fundSize: number` — stored in millions (1199.0 = $1,199M)
- `vintage: number` — the year the fund was established
- `managers: string[]` — array of manager firm names

---

## Questions You Might Get Asked

**"Why did you use array index as the ID?"**
> The JSON data had no unique ID field so I used the array index as the simplest available identifier. The tradeoff is that indices shift after a delete — in production I'd add a UUID to each record.

**"How does auto-save work?"**
> Every input field has a `(blur)` event binding that calls `autoSave()`. When the user tabs away or clicks out of a field, it builds the updated payload, converts the comma-separated strings back to arrays, and fires a PUT request to the backend. A save indicator shows the status.

**"How would you scale this beyond a JSON file?"**
> Replace `readFunds()` and `writeFunds()` with database queries. PostgreSQL for structured relational data or MongoDB if we want schema flexibility. Add an ORM like Prisma or Mongoose. The API route structure stays the same — only the data layer changes.

**"What would you add with more time?"**
> Unique IDs on each record, authentication so only admins can edit, input validation on the backend, optimistic UI updates so the edit feels instant, and proper error handling with user-facing messages.

**"Why three separate arrays in the table component?"**
> `allFunds` holds the full dataset and is never touched after load. `filteredFunds` is the result of search, year filter, and sort. `pagedFunds` is the current page slice of filtered results. Keeping them separate means filtering never destroys the original data — clearing a filter instantly restores everything.
