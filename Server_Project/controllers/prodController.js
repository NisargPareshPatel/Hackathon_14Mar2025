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

// Handler to get all cars listed by a specific lister
const getProdbyStore = async (req, res) => {
  const sid = req.body.sid;

  if (!sid) {
    return res.status(400).json({ message: "Store ID is required" });
  }

  if (!mongoose.Types.ObjectId.isValid(id)) {
    return res.status(404).json({ error: "Invalid ID" });
  }

  try {
    const products = await Prod.find({ store_id: sid });

    if (!products || products.length === 0) {
      return res
        .status(404)
        .json({ message: "No Products found for this Store" });
    }
    res.status(200).json(products);
  } catch (err) {
    console.error(err);
    res.status(500).send({ message: "Failed to fetch cars" });
  }
};

const booked = async (req, res) => {
  const id = req.body.id;

  if (!id) {
    return res.status(400).json({ message: "Error: ID not found" });
  }

  if (!mongoose.Types.ObjectId.isValid(id)) {
    return res.status(404).json({ error: "Invalid ID" });
  }

  try {
    const product = await Prod.findByIdAndUpdate(
      id,
      { booked: true },
      { new: true }
    );
    if (!product) return res.status(200).json({ message: "Product not found" });
    res.status(200).json(product);
  } catch (err) {
    console.error(err);
    res.status(500).send({ message: "Failed to reserve product" });
  }
};

// Handler to get a specific car by ID
const getProdbyID = async (req, res) => {
  const { id } = req.params;

  if (!mongoose.Types.ObjectId.isValid(id)) {
    return res.status(404).json({ error: "Invalid ID" });
  }

  const prod = await Prod.findById(id);
  if (!prod) {
    return res.status(404).json({ error: "prod does not exist" });
  }

  res.status(200).json(prod);
};

export { validateProd, createProd, getProdbyStore, booked, getProdbyID };
