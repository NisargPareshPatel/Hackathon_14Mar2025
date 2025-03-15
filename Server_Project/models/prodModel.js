//
//CarModel.sj component
//
//This file defines a Mongoose schema and model for managing car listings in the CaRnR web application.
//
//The Car schema includes fields to describe various attributes of a car, such as make, model, year,
//odometer reading, transmission type, fuel type, seating capacity, color, description, and daily rental rate.
//It also includes fields for location coordinates, availability dates and times, a photo URL, and identifiers
//for the lister and renter. The 'booked' field indicates whether the car is currently booked.
//
//
import mongoose from "mongoose";
import Email from "../controllers/mail.js";
const Schema = mongoose.Schema;

// Define the car schema with fields and their types
const prodSchema = new Schema({
 
  name: {
    type: String,
  }
  booked: {
    type: Boolean,
  },
  location: {
    type: { type: String, enum: ["Point"], default: "Point" },
    coordinates: { type: [Number], required: true },
  },
});

carSchema.index({ location: "2dsphere" });
// Create a Mongoose model for the Car schema
const Car = mongoose.model("Car", carSchema);

class CarModel {
  static observers = [];

  static addObserver(observer) {
    this.observers.push(observer);
  }

  static removeObserver(observer) {
    this.observers = this.observers.filter((o) => o !== observer);
  }

  static notifyObservers(data) {
    this.observers.forEach((observer) => observer.update(data));
  }

  static async bookcar(cid, rid) {
    const cars = await Car.findById(cid);

    if (!cars) {
      throw Error("Not Found");
    }
    cars.booked = true;
    cars.renterid = rid;
    await Car.replaceOne({ _id: cid }, cars);

    this.notifyObservers({ cid, rid });
  }
}

const email = new Email();
CarModel.addObserver(email);

export { Car, CarModel };
