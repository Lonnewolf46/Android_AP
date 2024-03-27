import express, { Request, Response } from "express";
import morgan from "morgan";
import apiRoutes from "./routes.js";

const app = express();
const PORT = process.env.PORT || 3000;

app.use(morgan("dev"));
app.use(express.json());

app.use("/api", apiRoutes);
app.use("*", (request: Request, response: Response) => response.status(404).send("Not Found"));

app.listen(PORT, "0.0.0.0", () => {
    console.log(`Server running in port ${PORT}`);
});