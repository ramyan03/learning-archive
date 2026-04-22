# Interview Prep — Daily Plan (Wed → Monday)

---

## Wednesday — Node.js + Express + REST API

### Watch
- Angela Yu — Node.js course (2 hrs)
- Angela Yu — Express.js course (2 hrs)
- Angela Yu — REST API course (2 hrs)

### After each video — do this
- Close the video
- Rewrite the key code from memory in a new file
- Run it and make sure it works
- Take notes in your own words

### Know cold by end of day
- What Node.js is and why it exists
- What Express adds on top of Node
- The full request lifecycle — request in, middleware, route handler, response out
- `app.use()` — what it does, why order matters
- `req.params` vs `req.query` vs `req.body` — the difference, when each is used
- All HTTP methods — GET, POST, PUT, PATCH, DELETE — when to use each
- All key status codes — 200, 201, 204, 400, 401, 403, 404, 500
- Why `express.json()` is needed
- Why `cors()` is needed
- `process.env.PORT || 3000` — what it does and why
- `path.join(__dirname, ...)` — what it does and why
- `readFileSync` vs `readFile` — sync vs async trade-off
- REST principles — nouns not verbs, stateless
- The double-response bug and how to fix it with else
- Error-handling middleware — 4-parameter signature

### Practice
- Open your take-home `index.ts` and explain every single line out loud as if teaching someone
- Fire all 4 routes in Postman — GET all, GET one, PUT, DELETE
- Try sending a bad index (e.g. /api/funds/9999) and confirm you get 404
- Try a PUT with only one field in the body and confirm other fields are preserved

---

## Thursday — SQL + Git + Security

### Watch
- Angela Yu — SQL course (3 hrs)

### Know cold by end of day — SQL
- SELECT, WHERE, ORDER BY, LIMIT, OFFSET
- COUNT, AVG, SUM, GROUP BY, HAVING
- INNER JOIN vs LEFT JOIN — know the difference, be able to draw it out
- What a junction table is (many-to-many relationships)
- What an index is — speeds up reads, slows writes slightly, add on frequently queried columns
- Primary key vs foreign key
- `CREATE INDEX idx_name ON table(column)`

### Practice SQL — use sqliteonline.com (no install)
Create this table and run queries against it:
```sql
CREATE TABLE funds (
  id INTEGER PRIMARY KEY,
  name TEXT,
  fund_size REAL,
  vintage INTEGER,
  currency TEXT
);

INSERT INTO funds VALUES (1, 'KIM Fund 3', 1199.0, 2025, 'CAD');
INSERT INTO funds VALUES (2, 'ZRE Fund 75', 1269.22, 2015, 'CNY');
INSERT INTO funds VALUES (3, 'LAZ Fund 24', 3827.87, 1993, 'EUR');
INSERT INTO funds VALUES (4, 'SFF Fund 87', 3600.04, 2014, 'USD');
INSERT INTO funds VALUES (5, 'RXZ Fund 6', 4867.88, 2000, 'USD');
```

Then practice:
```sql
-- get all funds sorted by size descending
-- get all USD funds
-- get funds with size over 3000
-- count funds per currency
-- get average fund size per vintage year
-- get top 3 largest funds
```

### Know cold by end of day — Git
- `git init`, `add`, `commit`, `push`, `pull`, `status`, `log --oneline`
- `git checkout -b feature/name` — create and switch to branch
- `git merge feature/name` — merge branch into current
- `git stash` — shelve uncommitted changes
- What a pull request is and the full workflow — branch → commit → push → PR → review → merge → delete branch
- How to read and resolve a merge conflict — the `<<<<<<<`, `=======`, `>>>>>>>` markers
- What `.gitignore` is for — node_modules, .env, dist

### Know cold by end of day — Security
- Least privilege — only return what the client needs, never expose sensitive fields
- Secrets in .env — never hardcode, never commit to git
- Input validation — validate before using in queries, prevents SQL injection and NoSQL injection
- CORS in production — restrict to known origin, not open `cors()`
- Authentication vs Authorization — auth = who are you, authz = what can you do
- HTTPS — encrypts data in transit, always in production
- Never store plain text passwords — use bcrypt to hash

---

## Friday — MongoDB + Redis + Background Jobs + Core Web Vitals

### Watch
- Angela Yu — MongoDB (1 hr)
- Redis overview video (30 mins — any YouTube intro is fine)
- Core Web Vitals (20 mins — Google's own video or web.dev)

### Know cold by end of day — MongoDB
- Collections vs tables, documents vs rows
- Documents are flexible JSON — no fixed schema
- `find`, `findOne`, `insertOne`, `updateOne`, `deleteOne`
- `$set`, `$inc`, `$push` operators
- `.sort()`, `.limit()`
- Referencing vs embedding — when to use each
- When to choose MongoDB over SQL and vice versa
- Indexing — `createIndex`, text indexes
- Your Spotify clone — be ready to explain collections, structure, one decision you made

### Know cold by end of day — Redis
- What it is — in-memory key-value store
- Why it is fast — lives in RAM not disk
- TTL — what it is, why you always set one
- Use cases — caching, sessions, rate limiting
- The caching pattern — check cache first, hit DB if miss, store result with TTL
- Data is lost on restart unless persistence is configured
- Basic commands — GET, SET, DEL, EXPIRE

### Know cold by end of day — Background Jobs
- What they are — tasks that run outside the request/response cycle
- Why you need them — tasks too slow for a request (reports, emails, file processing)
- Queue — producer adds jobs, worker processes them
- Scheduler/cron — runs on a time interval
- Key benefit — user gets instant response, heavy work happens separately
- BullMQ — common Node.js queue library (just know the name)

### Know cold by end of day — Core Web Vitals
- LCP (Largest Contentful Paint) — main content load speed, target under 2.5s
- FID (First Input Delay) — response to first interaction, target under 100ms
- CLS (Cumulative Layout Shift) — layout stability, target under 0.1
- How to improve LCP — optimize images, reduce server response time, use SSR
- How to improve FID — reduce JavaScript execution time
- How to improve CLS — set dimensions on images, avoid injecting content above existing content
- What SSR is and why it helps SEO and initial load performance
- Technical SEO basics — H1/H2/H3 hierarchy, meta title, meta description, semantic HTML

---

## Saturday — Full Review + Take-Home Deep Dive + Behavioural

### Morning — take-home review (2 hrs)
Run the project. Open every file. Go through each one and explain it out loud.

Must be able to answer these fluently without thinking:

**About the project:**
- Walk me through what you built
- How does auto-save work — why blur, not keystroke?
- Why did you use array index as the ID?
- What is the three-array pattern in the table component and why?
- Explain applyFilters() step by step
- Why do you spread both `funds[idx]` and `req.body` on the PUT route?
- Why does the frontend need the index added to the fund object?
- How does the delete flow work — why two steps?
- What does `cors()` do in your project specifically?
- What does `express.json()` do in your project specifically?
- What would you change or add with more time?
- How would you scale this beyond a JSON file?

### Afternoon — weak spots (2 hrs)
Go back through everything you found difficult during the week. Focus on:
- Any SQL queries you could not write from memory
- Any concepts from the Node/Express rundown that felt shaky
- MongoDB operators you forgot
- The caching pattern in Redis

### Evening — behavioural + company prep (1.5 hrs)

**About Setter Capital — know this:**
- They operate in the secondary market for private funds
- They help investors buy and sell fund interests before the fund matures (liquidity)
- Based in Toronto
- The take-home was literally their core business — managing fund data
- Small company — high ownership, direct impact, you will wear many hats
- The JD mentions frequent context switching and multiple projects simultaneously

**Tell me about yourself — keep it under 60 seconds:**
> "I am a full-stack developer comfortable with Angular on the front end and Node and Express on the back end. I recently completed a co-op and I am looking for a role where I can take real ownership of what I build. The take-home gave me a good sense of what the work at Setter looks like and I found it genuinely interesting."

**Why Setter specifically:**
> "The domain is interesting — I did not know much about secondary fund markets before this process but learning about what Setter does made the take-home make a lot more sense. I want to work somewhere where I can see the direct impact of what I build."

**Why should we hire you:**
> "I delivered a full working application in 24 hours, made independent UX decisions, and I can explain every line of the code. I pick things up fast and I am not afraid to ask questions when I do not know something."

**What is your biggest weakness:**
Keep it honest and growth-oriented:
> "I sometimes spend too long trying to figure something out on my own before asking for help. I have been getting better at recognizing when it is more efficient to just ask."

**Tell me about a challenge you faced:**
Use the take-home itself or your Spotify clone — describe the problem, what you did, what you learned.

**Where do you see yourself in a few years:**
> "Growing as a developer, getting deeper into the full stack, and ideally taking on more architectural responsibility as I get more experience."

**Questions to ask them — have these ready:**
- What does the team structure look like?
- What does a typical week look like for someone in this role?
- What does success look like in the first 3 months?
- What is the biggest technical challenge the team is working through right now?
- What does the stack look like beyond Angular and Node?

---

## Sunday — Practice + Logistics

### Morning — practice everything out loud (2 hrs)
Do not study new material. Only practice answers out loud.

Go through this list and answer each one as if you are in the room:
- Walk me through your take-home
- How does auto-save work?
- What is middleware in Express?
- What is the difference between req.params, req.query, and req.body?
- What is the difference between SQL and MongoDB?
- Write a SQL query to get all funds with size over 3000 sorted by vintage descending
- How would you cache a slow API endpoint?
- What happens in a git merge conflict?
- How would you handle a task that takes 30 seconds to complete?
- What is an index and when would you add one?
- What is CORS and why did you need it in your project?
- Tell me about your Spotify clone
- Why Setter Capital?
- Tell me about yourself

If you stumble on any of these — that is your signal to spend 20 more minutes on that topic.

### Afternoon — light review only (1 hr)
- Skim the checklist file and make sure everything is ticked
- Reread the take-home deep dive file once
- Do not start any new topics

### Evening — logistics
- Confirm the address — 2 Bloor St W, Suite 1700, Toronto ON M4W 3E2
- Plan your route — check Google Maps, TTC or parking
- Aim to arrive 10 minutes early — leave earlier than you think you need to
- Charge your laptop in case they ask you to open the project
- Make sure the project runs locally — backend on 3000, frontend on 4300
- Sleep at a reasonable hour — being rested matters more than one more hour of studying

---

## Monday Morning — Interview Day

- Eat breakfast
- Leave early
- Arrive at 2 Bloor St W, Suite 1700
- You are meeting Vlad and Peter
- Have the project ready to demo on your laptop
- Be ready to share your screen or open your editor

**If they ask you to live code:**
- Think out loud — say what you are doing before you type it
- Start with the simplest working solution, then optimize
- If you get stuck, say "let me think through this" — silence is worse than talking
- It is okay to say "I would normally look this up but here is what I remember"

**If they ask something you do not know:**
> "I have not worked with that directly but here is how I would approach it based on what I know"

Never bluff. They can tell. Honest and thoughtful beats confident and wrong every time.

---

## Quick Reference — Things to Know Cold on Monday

### Node/Express
- Middleware order and next()
- req.params vs req.query vs req.body
- All HTTP methods and status codes
- Why cors() and express.json() are needed
- The double-response bug

### SQL
- SELECT with WHERE, ORDER BY, LIMIT
- INNER JOIN vs LEFT JOIN
- GROUP BY and HAVING
- What an index does

### MongoDB
- Basic CRUD — find, insertOne, updateOne, deleteOne
- $set, $inc operators
- Referencing vs embedding
- When to use MongoDB vs SQL

### Redis
- What it is, why it is fast
- The caching pattern
- TTL

### Git
- Full branching workflow
- How to resolve a merge conflict

### Security
- Secrets in .env
- Authentication vs Authorization
- Input validation

### Your Take-Home
- Every file, every function, every decision
- Be able to demo it live
- Know what you would improve

### Setter Capital
- Secondary fund market — liquidity for private fund investors
- Based in Toronto
- Small team, high ownership, multiple products simultaneously
