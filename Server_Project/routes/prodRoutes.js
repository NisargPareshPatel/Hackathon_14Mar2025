import express from "express";
import {
  validateProd,
  createProd,
  getProdbyStore,
  booked,
  getProdbyID,
} from "../controllers/prodController.js";

// Creating an instance of the Express Router
const router = express.Router();

// Route to list a new car (POST request with car validation)
router.post("/create", validateProd, createProd);

router.post("/getProdbyStore", getProdbyStore);

router.post("/setBook", booked);

router.get("/:id", getProdbyID);

// Exporting the router to be used in the main application file
export default router;
