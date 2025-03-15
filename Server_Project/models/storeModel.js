import mongoose from "mongoose";
import bcrypt from "bcrypt";

// Define the Schema class from mongoose
const Schema = mongoose.Schema;

// Create a schema for the User model
const storeSchema = new Schema({
  name: {
    type: String,
    required: true,
  },
  email: {
    type: String,
    required: true,
    unique: true,
  },
  password: {
    type: String,
    required: true,
  },
  lat: {
    type: String,
  },
  long: {
    type: String,
  },
});

// Static method to handle user login
storeSchema.statics.login = async function (email, password) {
  // Check if all required fields are provided
  if (!email || !password) {
    throw Error("All fields must be filled");
  }

  // Find store by email
  const store = await this.findOne({ email });

  // Check if user exists
  if (!store) {
    throw Error("Email Incorrect");
  }

  // Compare provided password with stored hash
  const match = await bcrypt.compare(password, store.password);

  // Check if password matches
  if (!match) {
    throw Error("Password Incorrect");
  }

  return store;
};

// Create the User model using the schema and export it
const Store = mongoose.model("Store", storeSchema);
export default Store;
