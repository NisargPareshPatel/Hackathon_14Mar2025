import mongoose from "mongoose";
const Schema = mongoose.Schema;

// Define the prod schema with fields and their types
const prodSchema = new Schema({
  name: {
    type: String,
    required: true,
  },
  photo: {
    type: String,
    required: true,
  },
  expiry: {
    type: Date,
    required: true,
    index: { expires: 0 },
  },
  booked: {
    type: Boolean,
    default: false,
    required: true,
  },
  booker_id: {
    type: String,
  },
  price: {
    type: Number,
    required: true,
  },
  store_id: {
    type: String,
    required: true,
  },
});

// Create a Mongoose model for the prod schema
const prod = mongoose.model("prod", prodSchema);
export default prod;
