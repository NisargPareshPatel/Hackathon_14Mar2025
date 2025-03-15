//
//User Controller
//
//This file contains controller functions for handling user-related operations.
//
//- `loginUser`: Authenticates a user with email and password, and returns a JSON Web Token (JWT) along with user details.
//- `signupUser`: Registers a new user with provided details, and returns a JSON Web Token (JWT) along with user details.
//- `getUserById`: Fetches and returns user details (first, last name, email) by user ID.
//
//The functions utilize the User model for interacting with the database and the jsonwebtoken library for token management.
//

import User from "../models/userModel.js";
import jwt from "jsonwebtoken";
import validator from "validator";
import bcrypt from "bcrypt";
const createToken = (_id) => {
  return jwt.sign({ _id }, process.env.SECRET, { expiresIn: "3d" });
};

// Controller function to handle user login
const loginUser = async (req, res) => {
  const { email, password } = req.body;

  try {
    // Authenticate user using the User model's login method
    const user = await User.login(email, password);
    // Generate a token for the authenticated user
    const token = createToken(user._id);
    const { _id: id, first, last } = user;
    // Respond with user details and token
    res.status(200).json({ id, first, last, email, token });
  } catch (error) {
    // Respond with an error message if login fails
    res.status(400).json({ error: error.message });
  }
};

// Controller function to handle user signup
const signupUser = async (req, res) => {
  const { first, last, email, phone, password } = req.body;

  try {
    if (!first || !last || !email || !password || !phone) {
      throw new Error("All fields must be filled");
    }
    if (!validator.isEmail(email)) {
      throw new Error("Email not valid");
    }
    if (!validator.isStrongPassword(password)) {
      throw new Error("Password too weak");
    }
    if (!validator.isMobilePhone(phone)) {
      throw new Error("Phone Number is Invalid");
    }

    const Eexists = await User.findOne({ email });
    if (Eexists) {
      throw new Error("Email in use");
    }
    const Pexists = await User.findOne({ phone });
    if (Pexists) {
      throw new Error("Phone number in use");
    }

    const salt = await bcrypt.genSalt(10);
    const hash = await bcrypt.hash(password, salt);
    const user = await User.create({
      first,
      last,
      email,
      phone,
      password: hash,
    });
    const token = createToken(user._id);
    const id = user._id;
    // Respond with user details and token
    res.status(200).json({ id, first, last, email, phone, token });
  } catch (error) {
    // Respond with an error message if signup fails
    res.status(400).json({ error: error.message });
  }
};

// Controller function to get user details by their ID
const getUserById = async (req, res) => {
  const { id } = req.params;

  try {
    // Fetch user details by ID from the User model
    const user = await User.findById(id).select("first last email phone");
    if (!user) {
      throw new Error("User not found");
    }

    res.status(200).json(user);
  } catch (error) {
    // Respond with an error message if user not found
    res.status(404).json({ error: error.message });
  }
};

// Export the controller functions
export default loginUser;
export { signupUser, getUserById };
