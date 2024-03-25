import express, { Request, Response } from "express";
import morgan from "morgan";
import apiRoutes from "./routes.js";

const app = express();
const PORT = process.env.port || 3000;

app.use(morgan("dev"));
app.use(express.json());

app.use("/api", apiRoutes);
app.use("*", (request: Request, response: Response) => response.send("Not Found"));

app.listen(PORT, () => {
    console.log(`Server running in port ${PORT}`);
});