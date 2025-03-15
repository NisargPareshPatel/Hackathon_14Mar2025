//
//CarController module
//
//This module provides the API endpoints for managing car listings in the CaRnR web application.
//It includes functionality for:
//Validating car listing data using express-validator
//Listing a new car
//- Retrieving all available cars
//- Retrieving a specific car by ID
//- Retrieving cars listed by a specific lister
//- Retrieving cars booked by a specific renter
//- Booking and unbooking cars
//- Deleting car listings
//
//Dependencies:
//- express-validator for request validation
//- mongoose for interacting with MongoDB
//
//Each endpoint handles specific requests related to car listings and performs CRUD operations on the Car model.
//
import { check, validationResult } from "express-validator";
import Prod from "../models/prodModel.js";
import mongoose from "mongoose";

const validateProd = [
  check("name").notEmpty().withMessage("Please add a Name"),
  check("photo").notEmpty().withMessage("Please add a Photo"),
  check("expiry").notEmpty().withMessage("Please List a Date"),
  check("price").notEmpty().withMessage("Please List a Price"),
];

// Handler to list a new car
const createProd = async (req, res) => {
  // Validate request data
  const errors = validationResult(req);
  if (!errors.isEmpty()) {
    return res.status(400).json({ errors: errors.array() });
  }

  try {
    await Prod.create({
      name: req.body.name,
      photo: req.body.photo,
      expiry: req.body.expiry,
      booked: false,
      price: req.body.price,
      store_id: req.body.store_id,
    });
    res.status(200).send({ message: "Product listed successfully" });
  } catch (err) {
    console.error(err);
    res.status(500).send({ message: "Failed to list Product" });
  }
};

// const getallCarbylocation = async (req, res) => {
//   try {
//     // Find all cars that are not booked
//     const latitude = req.body.lat;
//     const longitude = req.body.lng;
//     const cars = await Car.aggregate([
//       {
//         $geoNear: {
//           near: {
//             type: "Point",
//             coordinates: [parseFloat(longitude), parseFloat(latitude)],
//           },
//           key: "location",
//           maxDistance: parseFloat(10) * 1609, // 1 mile in meters
//           distanceField: "dist.calculated",
//           // distanceField: "dist.calculated",
//           // maxDistance: 1609 * 1000, // 1 mile in meters
//           spherical: true,
//         },
//       },
//       {
//         $match: { booked: false }, // Filter out booked cars
//       },
//     ]);
//     res.status(200).json(cars);
//   } catch (err) {
//     console.error(err);
//     res.status(500).send({ message: "Failed to fetch cars" });
//   }
// };

// // Handler to get all available cars
// const getallCar = async (req, res) => {
//   try {
//     // Find all cars that are not booked
//     const cars = await Car.find({ booked: false });
//     res.status(200).json(cars);
//   } catch (err) {
//     console.error(err);
//     res.status(500).send({ message: "Failed to fetch cars" });
//   }
// };

// // Handler to get a specific car by ID
// const getCar = async (req, res) => {
//   const { id } = req.params;

//   // Validate car ID
//   if (!mongoose.Types.ObjectId.isValid(id)) {
//     return res.status(404).json({ error: "Invalid ID" });
//   }

//   const car = await Car.findById(id);
//   if (!car) {
//     return res.status(404).json({ error: "Car does not exist" });
//   }

//   res.status(200).json(car);
// };

// // Handler to get all cars listed by a specific lister
// const getlisterCars = async (req, res) => {
//   const lid = req.body.listerid;

//   if (!lid) {
//     return res.status(400).json({ message: "Lister ID is required" });
//   }

//   try {
//     const cars = await Car.find({ listerid: lid });

//     if (!cars || cars.length === 0) {
//       return res.status(404).json({ message: "No cars found for this lister" });
//     }
//     res.status(200).json(cars);
//   } catch (err) {
//     console.error(err);
//     res.status(500).send({ message: "Failed to fetch cars" });
//   }
// };

// // Handler to get all cars booked by a specific renter
// const getBookedCars = async (req, res) => {
//   const rid = req.body.rid;

//   if (!rid) {
//     return res.status(400).json({ message: "Renter ID is required" });
//   }

//   try {
//     const cars = await Car.find({ renterid: rid });

//     if (!cars || cars.length === 0) {
//       return res.status(404).json({ message: "No cars booked" });
//     }

//     res.status(200).json(cars);
//   } catch (err) {
//     console.error(err);
//     res.status(500).send({ message: "Failed to fetch cars" });
//   }
// };

// // Handler to mark a car as booked
// const booked = async (req, res) => {
//   const cid = req.body.cid;
//   const rid = req.body.rid;

//   if (!cid) {
//     return res.status(400).json({ message: "Error: Car ID not found" });
//   }

//   try {
//     await CarModel.bookcar(cid, rid);

//     res.status(200).json({ message: "Car successfully booked" });
//   } catch (err) {
//     console.error(err);
//     res.status(500).send({ message: "Failed to book car" });
//   }
// };

// // Handler to mark a car as unbooked
// const Unbooked = async (req, res) => {
//   const cid = req.body.cid;

//   if (!cid) {
//     return res.status(400).json({ message: "Error: Car ID not found" });
//   }

//   try {
//     const car = await Car.findById(cid);

//     if (!car) {
//       return res.status(404).json({ message: "Car not found" });
//     }

//     // Update car status to unbooked and clear renter ID
//     car.booked = false;
//     car.renterid = "";
//     await Car.replaceOne({ _id: cid }, car);

//     res.status(200).json({ message: "Car successfully unbooked" });
//   } catch (err) {
//     console.error(err);
//     res.status(500).send({ message: "Failed to unbook car" });
//   }
// };

// // Handler to delete a car listing
// const deleteList = async (req, res) => {
//   const cid = req.body.cid;

//   if (!cid) {
//     return res.status(400).json({ message: "Error: Car ID not found" });
//   }

//   try {
//     // Remove car from the database
//     await Car.deleteOne({ _id: cid });

//     res.status(200).json({ message: "Car successfully unlisted" });
//   } catch (err) {
//     console.error(err);
//     res.status(500).send({ message: "Failed to delete car" });
//   }
// };

// Export handlers and validation rules for use in other parts of the application
export {
  validateProd,
  createProd,
  // getallCar,
  // getCar,
  // getlisterCars,
  // booked,
  // Unbooked,
  // getBookedCars,
  // deleteList,
  // getallCarbylocation,
};
