# Loyalty Points System

A backend service for a retail loyalty program. Customers earn points on purchases, redeem them for rewards, and are assigned a tier based on rolling 12-month spend.

Built in **Java 21** with **Spring Boot**, backed by **SQLite**.

---

## Table of Contents

- [Running the Project](#running-the-project)
- [Data Model](#data-model)
- [Trade-offs](#trade-offs)
- [What I'd Change or Add With More Time](#what-id-change-or-add-with-more-time)
- [Business Logic Decisions](#business-logic-decisions)
- [Endpoints](#endpoints)
- [AI Tools Used](#ai-tools-used)

---

## Running the Project

**Requirements:** Java 21, Maven

From the project root:

```bash
mvn clean install
mvn clean compile
mvn spring-boot:run
```

The server starts on **port 8080**. A `loyalty.db` SQLite file is created automatically on first run, and all endpoints listed above become reachable at `http://localhost:8080`.

A Postman Collection is included in the project artifacts as well to make using the demo easier. Simply import the included `Loyalty Rewards Program.postman_collection.json` into Postman to use.

## Data Model

The schema centers on **Customers** and **Purchases**, with a few supporting tables:

| Relationship | Type |
|---|---|
| Customer → Purchase | one-to-many |
| Customer → Redemption | one-to-many |
| Reward | lookup table |

This layout was chosen because it's a logical structure that was quick to model and implement without sacrificing clarity.

---

## Trade-offs

- **Points live on `Purchase`, not a separate ledger table.** Rather than breaking points out into their own tracking table, they're rolled directly into the `Purchase` row (`points` column) for simplicity.
    - The `points` column is **mutable** and it's decremented directly when points are redeemed. A more granular approach (separate `pointsEarned` / `pointsSpent` columns) would enable better analytics and would correctly handle cases where dollars-spent and points-earned aren't strictly 1:1.
    - Points are updated directly inside `PurchaseService` and `RefundService`, which blurs the responsibility boundary of the dedicated `PointsService`. Chosen for implementation simplicity.

- **Balance calculation doesn't scale.** The current approach for computing a customer's point status is fine for a small SQLite database or proof-of-concept, but would need a more efficient tracking model at real scale.

- **SQLite limitations, accepted for a local demo:**
    - No native `BOOLEAN` type, represented as `0`/`1` instead.
    - Single-writer limitation (fine for a locally running demo; would need reconsidering for concurrent production use).

- **Timestamps stored as Unix time, not human-readable text.** Rather than forcing the JDBC driver to store human-readable timestamps, I kept the driver's native Unix-time format to avoid inconsistent comparisons across repositories (mixing TEXT and INTEGER timestamp formats caused bugs during development). Foreign key enforcement was explicitly turned on via the datasource URL, since SQLite has it disabled by default.

---

## What I'd Change or Add With More Time

- **Automated tests.** I tested manually using a Postman collection (included in the solution), but I'd add unit tests covering each service's core scenarios (earning, redeeming, refunding, expiry, tier boundaries) for better long-term coverage and regression safety.
- `data.sql` currently seeds sample users and purchases to make manual testing easier. I'd extend this with a broader range of edge-case data (expired points, partially redeemed purchases, refunded purchases) to make manual verification faster.

---

## Business Logic Decisions

1. **Points round down to whole numbers.** A purchase amount that isn't a whole dollar figure has its point value floored. Example: **$10.75 → 10 points.**
2. **No refunds on purchases with already-redeemed points.** To prevent abuse (redeem a reward, then refund the purchase that funded it), a purchase cannot be refunded if any of its points have already been used toward a redemption.

---

## Endpoints

| Method | Path | Description |
|---|---|---|
| `GET`  | `/customers` | List all customers |
| `GET`  | `/customers/{id}/balance` | Current point balance + tier |
| `POST` | `/customers/{id}/purchases` | Record a purchase, earning points |
| `GET`  | `/customers/{id}/redemptions` | Redemption history for a customer |
| `POST` | `/customers/{id}/redemptions` | Redeem points for a reward |
| `POST` | `/purchases/{id}/refund` | Refund a purchase, clawing back points |

---

## AI Tools Used

Claude was used throughout this project to help scaffold boilerplate entities, repositories, DTOs, and response objects.

---