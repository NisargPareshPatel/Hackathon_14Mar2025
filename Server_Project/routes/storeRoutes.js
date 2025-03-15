import express from "express"; // Express framework for routing
import {
  loginStore,
  signupStore,
  getStoreById,
  getStoresWithProd,
} from "../controllers/storeController.js"; // Import store controllers

const router = express.Router(); // Create an Express Router instance

// Route to handle store login
router.post("/login", loginStore);

// Route to handle store signup
router.post("/signup", signupStore);

router.get("/getStores", getStoresWithProd);

// Route to fetch store details by store ID
router.get("/:id", getStoreById);

// Export the router instance for use in other parts of the application
export default router;
