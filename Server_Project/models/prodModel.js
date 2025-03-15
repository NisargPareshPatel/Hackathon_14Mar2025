//
//prodModel.sj component
//
//This file defines a Mongoose schema and model for managing prod listings in the prodnR web application.
//
//The prod schema includes fields to describe various attributes of a prod, such as make, model, year,
//odometer reading, transmission type, fuel type, seating capacity, color, description, and daily rental rate.
//It also includes fields for location coordinates, availability dates and times, a photo URL, and identifiers
//for the lister and renter. The 'booked' field indicates whether the prod is currently booked.
//
//
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
  },
  booked: {
    type: Boolean,
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
