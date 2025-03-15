import { check, validationResult } from "express-validator";
import Prod from "../models/prodModel.js";
import mongoose from "mongoose";

const validateProd = [
  check("name").notEmpty().withMessage("Please add a Name"),
  check("photo").notEmpty().withMessage("Please add a Photo"),
  check("expiry")
    .notEmpty()
    .withMessage("Please List a Date")
    .custom((value) => {
      const expiryDate = new Date(value);
      if (isNaN(expiryDate.getTime())) {
        throw new Error("Invalid date format");
      }
      if (expiryDate <= new Date()) {
        throw new Error("Expiry date must be in the future");
      }
      return true;
    }),
  check("price").notEmpty().withMessage("Please List a Price"),
];

const createProd = async (req, res) => {
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

const getProdbyStore = async (req, res) => {
  const sid = req.body.sid;

  if (!sid) {
    return res.status(400).json({ message: "Store ID is required" });
  }

  if (!mongoose.Types.ObjectId.isValid(sid)) {
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
  const { prod_id, booker_id } = req.body;

  if (!prod_id) {
    return res.status(400).json({ message: "Error: ID not found" });
  }

  if (!mongoose.Types.ObjectId.isValid(prod_id)) {
    return res.status(404).json({ error: "Invalid ID" });
  }

  try {
    const product = await Prod.findById(prod_id);
    product.booked = true;
    product.booker_id = booker_id;
    await product.save();
    if (!product) return res.status(200).json({ message: "Product not found" });
    res.status(200).json(product);
  } catch (err) {
    console.error(err);
    res.status(500).send({ message: "Failed to reserve product" });
  }
};

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
