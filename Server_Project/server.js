import "dotenv/config";
import express from "express";
import cors from "cors";
import prodRoutes from "./routes/prodRoutes.js";
import userRoutes from "./routes/userRoutes.js";
import storeRoutes from "./routes/storeRoutes.js";
import mongoose from "mongoose";

// Set the port number from environment variable or default to 4000
const PORT = process.env.PORT || 4000;

// Initialize Express application
const app = express();

// Middleware to enable CORS (Cross-Origin Resource Sharing)
app.use(cors());

// Middleware to parse JSON request bodies
app.use(express.json());

// Serve static files from the 'uploads' directory
app.use("/uploads", express.static("uploads"));

// Middleware to log the request path and method
app.use((req, res, next) => {
  console.log(req.path, req.method);
  next();
});

// Set up routes for car-related operations
app.use("/api/prodRoutes", prodRoutes);

// Set up routes for user-related operations
app.use("/api/userRoutes", userRoutes);

app.use("/api/storeRoutes", storeRoutes);

// Connect to MongoDB and start the server if successful
mongoose
  .connect(process.env.ATLUS_URI)
  .then(() => {
    // Listen for incoming requests on the specified port
    app.listen(PORT, () => {
      console.log("Connected to DB & listening on port", PORT);
    });
  })
  .catch((error) => {
    // Log any errors encountered during the connection
    console.log(error);
  });
