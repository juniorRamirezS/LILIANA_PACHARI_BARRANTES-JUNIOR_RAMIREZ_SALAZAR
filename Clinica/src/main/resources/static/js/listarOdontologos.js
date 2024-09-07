//const apiURL = "http://localhost:8080";

// Obtener la referencia a la tabla y al modal
const tableBody = document.querySelector("#odontologoTable tbody");
const editModal = new bootstrap.Modal(document.getElementById("editModal"));
const editForm = document.getElementById("editForm");

let currentOdontologoId;
let currentPacienteId;
let currentDomicilioId;

// Función para obtener y mostrar los odontólogos
function fetchPacientes() {
  // listar los odontologos
  fetch(`odontologo/buscarTodos`)
    .then((response) => response.json())
    .then((data) => {
      console.log(data);
      // Limpiar el contenido actual de la tabla
      tableBody.innerHTML = "";

      // Insertar los datos en la tabla
      data.forEach((odontologo, index) => {
        const row = document.createElement("tr");

        row.innerHTML = `
              <td>${odontologo.id}</td>
              <td>${odontologo.nombre}</td>
              <td>${odontologo.apellido}</td>
              <td>${odontologo.numeroDeMatricula}</td>
           
              <td>
                <button class="btn btn-primary btn-sm" onclick="editOdontologo(${odontologo.id}, '${odontologo.apellido}','${odontologo.nombre}', '${odontologo.numeroDeMatricula}')">Modificar</button>
                <button class="btn btn-danger btn-sm" onclick="deleteOdontologo(${odontologo.id})">Eliminar</button>
              </td>
            `;

        tableBody.appendChild(row);
      });
    })
    .catch((error) => {
      console.error("Error fetching data:", error);
    });
}

// Función para abrir el modal y cargar los datos del odontologo
editOdontologo = function (
  id,
  apellido,
  nombre,
  numeroDeMatricula
) {
  currentOdontologoId = id;
//   currentPacienteId = id;
//   currentDomicilioId = idDomicilio;
  document.getElementById("editApellido").value = apellido;
  document.getElementById("editNombre").value = nombre;
  document.getElementById("editNumeroDeMatricula").value = numeroDeMatricula;

  editModal.show();
};

// Función para editar un paciente
editForm.addEventListener("submit", function (event) {
  event.preventDefault();
  const apellido = document.getElementById("editApellido").value;
  const nombre = document.getElementById("editNombre").value;
  const numeroDeMatricula = document.getElementById("editNumeroDeMatricula").value;


  //modificar un paciente
  fetch(`odontologo/modificar`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      id: currentOdontologoId,
      nombre,
      apellido,
      numeroDeMatricula
    }),
  })
    .then((response) => response.json())
    .then((data) => {
      console.log(data);
      alert("Odontólogo modificado con éxito");
      fetchPacientes();
      editModal.hide();
    })
    .catch((error) => {
      console.error("Error editando Odontólogo:", error);
    });
});

// Función para eliminar un paciente
deleteOdontologo = function (id) {
  if (confirm("¿Está seguro de que desea eliminar este odontólogo?")) {
    // eliminar el paciente
    fetch(`odontologo/eliminar/${id}`, {
      method: "DELETE",
    })
      .then((response) => response.json())
      .then((data) => {
        console.log(data);
        alert("Odontólogo eliminado con éxito");
        fetchPacientes();
      })
      .catch((error) => {
        console.error("Error borrando Odontólogo:", error);
      });
  }
};

// Llamar a la función para obtener y mostrar los odontólogos
fetchPacientes();
