const apiURL = "http://localhost:8080";

// Obtener la referencia a la tabla y al modal
const tableBody = document.querySelector("#odontologoTable tbody");
const editModal = new bootstrap.Modal(document.getElementById("editModal"));
const editForm = document.getElementById("editForm");
let currentPacienteId;
let currentDomicilioId;

// Función para obtener y mostrar los odontólogos
function fetchOdontologos() {
  // listar los pacientes
  fetch(`${apiURL}/odontologo/buscartodos`)
    .then((response) => response.json())
    .then((data) => {
      console.log(data);
      // Limpiar el contenido actual de la tabla
      tableBody.innerHTML = "";

      // Insertar los datos en la tabla
      data.forEach((odont, index) => {
        const row = document.createElement("tr");

        row.innerHTML = `
              <td>${odont.id}</td>
              <td>${odont.apellido}</td>
              <td>${odont.nombre}</td>
              <td>${odont.dni}</td>
              <td>${odont.fechaIngreso}</td>
              <td>${odont.domicilio.calle}</td>
              <td>${odont.domicilio.numero}</td>
              <td>${odont.domicilio.localidad}</td>
              <td>${odont.domicilio.provincia}</td>
              <td>
                <button class="btn btn-primary btn-sm" onclick="editPaciente(${odont.id}, '${odont.apellido}','${odont.nombre}', '${odont.dni}', 
                '${odont.fechaIngreso}', '${odont.domicilio.id}', '${odont.domicilio.calle}', '${odont.domicilio.numero}', '${odont.domicilio.localidad}', '${odont.domicilio.provincia}')">Modificar</button>
                <button class="btn btn-danger btn-sm" onclick="deleteOdontologo(${odont.id})">Eliminar</button>
              </td>
            `;

        tableBody.appendChild(row);
      });
    })
    .catch((error) => {
      console.error("Error fetching data:", error);
    });
}

// Función para abrir el modal y cargar los datos del paciente
editOdontologo = function (
  id,
  apellido,
  nombre,
  dni,
  fechaIngreso,
  idDomicilio,
  calle,
  numero,
  localidad,
  provincia
) {
  currentOdontologoId = id;
  currentDomicilioId = idDomicilio;
  document.getElementById("editApellido").value = apellido;
  document.getElementById("editNombre").value = nombre;
  document.getElementById("editDni").value = dni;
  document.getElementById("editFecha").value = fechaIngreso;
  document.getElementById("editCalle").value = calle;
  document.getElementById("editNumero").value = numero;
  document.getElementById("editLocalidad").value = localidad;
  document.getElementById("editProvincia").value = provincia;
  editModal.show();
};

// Función para editar un paciente
editForm.addEventListener("submit", function (event) {
  event.preventDefault();
  const apellido = document.getElementById("editApellido").value;
  const nombre = document.getElementById("editNombre").value;
  const dni = document.getElementById("editDni").value;
  const fecha = document.getElementById("editFecha").value;
  const calle = document.getElementById("editCalle").value;
  const numero = document.getElementById("editNumero").value;
  const localidad = document.getElementById("editLocalidad").value;
  const provincia = document.getElementById("editProvincia").value;

  //modificar un paciente
  fetch(`${apiURL}/odontologo/modificar`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({
      id: currentOdontologoId,
      nombre,
      apellido,
      dni,
      fechaIngreso: fecha,
      domicilio: {
        id: currentDomicilioId,
        calle,
        numero,
        localidad,
        provincia,
      },
    }),
  })
    .then((response) => response.json())
    .then((data) => {
      console.log(data);
      alert("Odontologo modificado con éxito");
      fetchPacientes();
      editModal.hide();
    })
    .catch((error) => {
      console.error("Error editando odontologo:", error);
    });
});

// Función para eliminar un paciente
deleteOdontologo = function (id) {
  if (confirm("¿Está seguro de que desea eliminar este odontologo?")) {
    // eliminar el paciente
    fetch(`${apiURL}/odontologo/eliminar/${id}`, {
      method: "DELETE",
    })
      .then((response) => response.json())
      .then((data) => {
        console.log(data);
        alert("Odontologo eliminado con éxito");
        fetchPacientes();
      })
      .catch((error) => {
        console.error("Error borrando odontologo:", error);
      });
  }
};

// Llamar a la función para obtener y mostrar los odontólogos
fetchOdontologos();
