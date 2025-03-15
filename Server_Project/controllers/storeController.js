import Store from "../models/storeModel.js"; // Import Store model
import jwt from "jsonwebtoken";
import Factory from "./factory.js";

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
    const { _id: id, name, location } = store;
    // Respond with store details and token
    res.status(200).json({ id, name, location, email, token });
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
      location: {
        coordinates: [data.body.long, data.body.lat],
      },
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
    const store = await Store.findById(id).select("name location email");
    if (!store) {
      throw new Error("Store not found");
    }

    res.status(200).json(store);
  } catch (error) {
    // Respond with an error message if store not found
    res.status(404).json({ error: error.message });
  }
};

// Export the controller functions
export { loginStore, signupStore, getStoreById };
