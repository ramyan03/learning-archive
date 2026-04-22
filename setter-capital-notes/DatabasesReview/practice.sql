/*

db.grid.insertOne({...})
db.grid.insertMany([...])

db.grid.find()
db.grid.find({ name: "Max Verstappen" })

db.grid.updateOne(filter, { $set: {...} })
db.grid.updateMany(filter, { $set: {...} })
db.grid.updateMany({}, { $set: { active: true } })

db.grid.deleteOne(filter)
db.grid.deleteMany(filter)
db.grid.deleteOne({ _id: ObjectId("...") })

{ wins: { $gt: 10 } }
{ wins: { $gte: 10 } }
{ wins: { $lt: 10 } }
{ wins: { $lte: 10 } }

{ name: { $ne: "Max Verstappen" } }

{ wins: { $gte: 10, $lte: 100 } }

{ team: { $in: ["Haas", "Racing Bulls"] } }

{ $and: [ {...}, {...} ] }
{ $or:  [ {...}, {...} ] }
{ $nor: [ {...}, {...} ] }

{ wins: { $not: { $lte: 10 } } }

db.grid.find().sort({ wins: -1 })  // descending
db.grid.find().limit(5)
db.grid.find().skip(5).limit(5)

db.grid.createIndex({ name: 1 })
db.grid.getIndexes()
db.grid.dropIndex("name_1")

db.createCollection("principals")

db.createCollection("principals", {
  capped: true,
  size: 1000000,
  max: 11
})

db.grid.find(
  { wins: { $gte: 10 } },
  { name: 1, wins: 1, _id: 0 }
)
// Projection -> Control returned fields, remove ID from response, show wins and name for those with greater or equal to 10 wins

db.grid.aggregate([
  {
    $group: {
      _id: "$team",
      totalWins: { $sum: "$wins" }
    }
  }
])
// Group by team then sum wins of each driver of that team

db.grid.countDocuments()
db.grid.countDocuments({ wins: { $gte: 10 } })
// Count number of elements

db.grid.distinct("team")
// Display distinct

*/

/*

Embedded Documents: Stored related data inside same document. For instance, we store country and principal of F1 team inside driver
    Faster reads, no joins necessary, simple queries
    But more data duplication, harder to update

Referencing: Store data separately and link via IDs/fields. Here, we have a separate collection for teams where we will have country and principal. We can join using $lookup
    Slower reads
    But easier to update and less duplication

Denormalization (Embed): Duplicate data for performance. Use when data is read frequently and doesn't change often.ABORT
Normalization (Reference): Keep data separate for consistency. Use when data changes often, shared across many documents and to avoid duplication

Data accessed together should be stored together

Optimize for read patterns, query frequency


*/
