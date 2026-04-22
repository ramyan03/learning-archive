# Interview Prep Checklist — Setter Capital

---

## Node.js + Express
- [ ] Request/response cycle — how a request flows through Express
- [ ] Middleware — what it is, `app.use()`, order matters
- [ ] `req.body` vs `req.params` vs `req.query` — know the difference
- [ ] Route parameters — `/api/funds/:id` → `req.params.id`
- [ ] Query strings — `/api/funds?page=2` → `req.query.page`
- [ ] Error handling middleware — `(err, req, res, next)`
- [ ] Environment variables — `process.env.PORT`, why never hardcode
- [ ] `fs` module — `readFileSync`, `writeFileSync`
- [ ] `path.join(__dirname, ...)` — why over hardcoded paths
- [ ] Async vs sync — when to use each
- [ ] `npm` basics — `package.json`, `node_modules`, scripts

---

## REST API Design
- [ ] HTTP methods — GET, POST, PUT, PATCH, DELETE and when to use each
- [ ] Status codes — memorize these:
  - [ ] `200` OK
  - [ ] `201` Created
  - [ ] `400` Bad Request
  - [ ] `401` Unauthorized
  - [ ] `403` Forbidden
  - [ ] `404` Not Found
  - [ ] `500` Internal Server Error
- [ ] REST principles — stateless, resources as nouns not verbs
- [ ] Request headers — `Content-Type`, `Authorization`
- [ ] Nested routes — `/api/funds/:id/managers`
- [ ] Difference between REST and GraphQL (conceptual)
- [ ] Input validation — validate before using in queries
- [ ] Never expose sensitive fields in responses
- [ ] Test all your own routes in Postman

---

## SQL
- [ ] `SELECT`, `WHERE`, `ORDER BY`, `LIMIT`, `OFFSET`
- [ ] `COUNT`, `AVG`, `SUM`, `MIN`, `MAX`
- [ ] `GROUP BY` and `HAVING`
- [ ] `INNER JOIN` — only matching rows from both tables
- [ ] `LEFT JOIN` — all rows from left, matched from right
- [ ] Junction tables — how many-to-many relationships work
- [ ] Indexing — what it is, when to add one, tradeoff on writes
- [ ] `CREATE INDEX` syntax
- [ ] Primary keys vs foreign keys
- [ ] Practice on [sqliteonline.com](https://sqliteonline.com) — write real queries

---

## MongoDB
- [ ] Collections vs tables, documents vs rows
- [ ] `find`, `findOne`, `insertOne`, `updateOne`, `deleteOne`
- [ ] `$set`, `$inc`, `$push` operators
- [ ] Sorting — `.sort()`, limiting — `.limit()`
- [ ] Referencing vs embedding — when to use each
- [ ] Indexing — `createIndex`, text indexes
- [ ] When to choose MongoDB over SQL
- [ ] Relate back to your Spotify clone — collections, structure, decisions

---

## Redis
- [ ] What it is — in-memory key-value store
- [ ] Why it's fast — lives in RAM not disk
- [ ] TTL — what it is and why you always set one
- [ ] Common use cases — caching, sessions, rate limiting
- [ ] Basic commands — `GET`, `SET`, `DEL`, `EXPIRE`
- [ ] Caching pattern — check cache first, hit DB if miss, store result
- [ ] What happens on restart — data loss unless persistence configured
- [ ] When NOT to use Redis — data that changes constantly

---

## Background Jobs & Queues
- [ ] What a background job is — runs outside request cycle
- [ ] Why you need them — slow tasks (emails, reports, file processing)
- [ ] Queue — producer adds jobs, worker processes them
- [ ] Scheduler / cron — runs on a time interval
- [ ] Key benefit — user gets instant response, heavy work happens separately
- [ ] BullMQ / Bull — common Node.js queue libraries (just know the name)

---

## Core Web Vitals & SEO
- [ ] LCP (Largest Contentful Paint) — main content load speed, target < 2.5s
- [ ] FID (First Input Delay) — response to first interaction, target < 100ms
- [ ] CLS (Cumulative Layout Shift) — layout stability, target < 0.1
- [ ] How to improve LCP — optimize images, reduce server response time
- [ ] How to improve FID — reduce JS execution time
- [ ] How to improve CLS — set dimensions on images, avoid dynamic content injection
- [ ] Technical SEO basics — H1/H2 tags, meta title, meta description, semantic HTML
- [ ] SSR (Server Side Rendering) — why it helps SEO and initial load

---

## Git
- [ ] `init`, `add`, `commit`, `push`, `pull`, `status`, `log`
- [ ] Branching — `checkout -b`, `merge`, `branch -d`
- [ ] Pull request workflow — branch → commit → PR → review → merge
- [ ] Merge conflicts — how to read and resolve them
- [ ] `git stash` — shelve uncommitted changes temporarily
- [ ] `git diff` — see changes before committing
- [ ] `.gitignore` — what to exclude (node_modules, .env, dist)

---

## Security
- [ ] Least privilege — only return what the client needs
- [ ] Secrets in `.env` — never in code, never committed
- [ ] Input validation — validate before using in queries
- [ ] SQL injection — what it is and how validation prevents it
- [ ] CORS — what it is, restrict to known origins in production
- [ ] HTTPS — encrypts data in transit
- [ ] Authentication vs Authorization — know the difference
- [ ] Never store plain text passwords — hashing (bcrypt)

---

## Your Take-Home Project
- [ ] Walk through all three views fluently — table, detail, edit
- [ ] Explain auto-save — blur event, why not keystroke, PUT request
- [ ] Explain the three arrays — allFunds, filteredFunds, pagedFunds
- [ ] Explain `applyFilters()` — search + year + sort in one function
- [ ] Explain `readFunds` / `writeFunds` — why synchronous, production alternative
- [ ] Explain why array index as ID — tradeoff, what you'd do in production
- [ ] Explain CORS setup — why needed, frontend on 4300 backend on 3000
- [ ] Explain the delete flow — two step, why confirmation modal
- [ ] Explain route structure — why `/funds/:index/detail` not `/detail/:index`
- [ ] Be ready to live demo — make sure it runs locally before Monday

---

## Practice Questions — Answer Out Loud
- [ ] Walk me through your take-home project
- [ ] How does auto-save work?
- [ ] Why array index as the ID?
- [ ] What's the difference between SQL and MongoDB?
- [ ] How would you cache a slow API endpoint?
- [ ] What happens in a git merge conflict?
- [ ] How would you scale the JSON file to a real database?
- [ ] What would you add with more time?
- [ ] Tell me about your Spotify clone
- [ ] What is middleware?
- [ ] What's the difference between authentication and authorization?
- [ ] How would you handle a task that takes 30 seconds to complete?
- [ ] What is an index and when would you add one?

---

## Before Monday
- [ ] Project runs locally — backend on 3000, frontend on 4300
- [ ] All four API routes work in Postman
- [ ] Know the address — 2 Bloor St W, Suite 1700, Toronto
- [ ] Plan your route — check transit or parking ahead of time
- [ ] Leave early — aim to arrive 10 mins before 9:30am
- [ ] Sleep well Sunday night
