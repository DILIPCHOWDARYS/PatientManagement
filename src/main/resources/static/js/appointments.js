let allDoctors = [];

async function fetchDoctors() {
  try {
    const response = await fetch('/api/doctors');
    allDoctors = await response.json();
  } catch (error) {
    console.error('Error fetching doctors:', error);
  }
}

function updateDoctorDropdown() {
  const dept = document.getElementById("department").value;
  const doctorSelect = document.getElementById("doctorId");

  // Reset the dropdown
  doctorSelect.innerHTML = '<option value="" disabled selected>Select Doctor</option>';

  // Filter doctors based on department
  const filtered = allDoctors.filter(doc => doc.specialization === dept);

  // Populate dropdown with names only (IDs hidden from view)
  filtered.forEach(doc => {
    const option = document.createElement("option");
    option.value = doc.id;         // 🔐 Sent in form on submission
    option.textContent = doc.name; // 👁️ Only name shown to user
    doctorSelect.appendChild(option);
  });
}

// Load doctors once when page loads
window.onload = fetchDoctors;
