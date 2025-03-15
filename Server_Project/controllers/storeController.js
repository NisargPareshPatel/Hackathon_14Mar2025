import Store from "../models/storeModel.js"; // Import Store model
import jwt from "jsonwebtoken";
import validator from "validator";
import bcrypt from "bcrypt";
import Prod from "../models/prodModel.js";

// Function to create a JWT token
const createToken = (_id) => {
  return jwt.sign({ _id }, process.env.SECRET, { expiresIn: "30d" });
};

// Controller function to handle store login
const loginStore = async (req, res) => {
  const { email, password } = req.body;

  try {
    // Authenticate store using the Store model's login method
    const store = await Store.login(email, password);
    // Generate a token for the authenticated store
    const token = createToken(store._id);
    const { _id: id, name, lat, long } = store;
    // Respond with store details and token
    res.status(200).json({ id, name, lat, long, email, token });
  } catch (error) {
    // Respond with an error message if login fails
    res.status(400).json({ error: error.message });
  }
};

// Controller function to handle store signup
const signupStore = async (req, res) => {
  const { name, lat, long, email, password } = req.body;

  try {
    if (!name || !lat || !long || !email || !password) {
      throw new Error("All fields must be filled");
    }
    if (!validator.isEmail(email)) {
      throw new Error("Email not valid");
    }
    if (!validator.isStrongPassword(password)) {
      throw new Error("Password too weak");
    }

    const exists = await Store.findOne({ email });
    if (exists) {
      throw new Error("Email in use");
    }
    const salt = await bcrypt.genSalt(10);
    const hash = await bcrypt.hash(password, salt);
    const store = await Store.create({
      name,
      email,
      password: hash,
      lat,
      long,
    });
    const token = createToken(store._id);
    const id = store._id;
    // Respond with store details and token
    res.status(200).json({ id, name, lat, long, email, token });
  } catch (error) {
    // Respond with an error message if signup fails
    res.status(400).json({ error: error.message });
  }
};

// Controller function to get store details by ID
const getStoreById = async (req, res) => {
  const { id } = req.params;

  try {
    // Fetch store details by ID from the Store model
    const store = await Store.findById(id).select("name email");
    if (!store) {
      throw new Error("Store not found");
    }

    res.status(200).json(store);
  } catch (error) {
    // Respond with an error message if store not found
    res.status(404).json({ error: error.message });
  }
};

const getStoresWithProd = async (req, res) => {
  try {
    const products = await Prod.find({
      store_id: { $exists: true },
      booked: false,
    }).distinct("store_id");

    if (!products.length) {
      return res
        .status(404)
        .json({ message: "No stores found with available products" });
    }

    const stores = await Store.find({ _id: { $in: products } });
    res.status(200).json(stores);
  } catch (err) {
    console.error(err);
    res.status(500).send({ message: "Failed to fetch Stores" });
  }
};

// Export the controller functions
export { loginStore, signupStore, getStoreById, getStoresWithProd };
