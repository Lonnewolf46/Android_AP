import express from 'express';

// Crear una instancia de Express
const app = express();

// Definir una ruta básica
app.get('/', (req, res) => {
  res.send('¡Hola mundo desde Express con EcmaScript!');
});

// Escuchar en el puerto 3000
app.listen(process.env.PORT || 3000, () => {
  console.log('El servidor está corriendo en http://localhost:3000');
});