import mongoose from "mongoose";
import bcrypt from "bcrypt";
import validator from "validator";

// Define the Schema class from mongoose
const Schema = mongoose.Schema;

// Create a schema for the User model
const userSchema = new Schema({
  first: {
    type: String,
    required: true,
  },

  last: {
    type: String,
    required: true,
  },
  email: {
    type: String,
    required: true,
    unique: true,
  },
  phone: {
    type: String,
    required: true,
  },
  password: {
    type: String,
    required: true,
  },
});

// Static method to handle user login
userSchema.statics.login = async function (email, password) {
  // Check if all required fields are provided
  if (!email || !password) {
    throw Error("All fields must be filled");
  }

  // Find user by email
  const user = await this.findOne({ email });

  // Check if user exists
  if (!user) {
    throw Error("Email Incorrect");
  }

  // Compare provided password with stored hash
  const match = await bcrypt.compare(password, user.password);

  // Check if password matches
  if (!match) {
    throw Error("Password Incorrect");
  }

  return user;
};

// Create the User model using the schema and export it
const User = mongoose.model("User", userSchema);
export default User;
