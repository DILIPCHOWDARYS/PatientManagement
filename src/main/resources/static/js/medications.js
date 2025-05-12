document.addEventListener("DOMContentLoaded", () => {
  const form = document.getElementById("medicationForm");

  form.addEventListener("submit", async (e) => {
    e.preventDefault();

    const patientName = document.getElementById("patientName").value.trim();
    const entries = document.querySelectorAll(".medication-entry");
    const medications = [];

    entries.forEach(entry => {
      const medicineName = entry.querySelector(".medicineName").value.trim();
      const dosage = entry.querySelector(".dosage").value.trim();
      const frequency = entry.querySelector(".frequency").value.trim();
      const duration = entry.querySelector(".duration").value.trim();

      if (medicineName && dosage && frequency && duration) {
        medications.push({ patientName, medicineName, dosage, frequency, duration });
      }
    });

    if (medications.length === 0) {
      alert("Please enter at least one valid medication.");
      return;
    }

    try {
      const response = await fetch("/api/medications/bulk", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(medications)
      });

      if (!response.ok) {
        const errorText = await response.text();
        console.error("❌ Error Response:", errorText);
        throw new Error("Failed to save medications.");
      }

      document.getElementById("successMessage").style.display = "block";
      form.reset();
      document.getElementById("medicationList").innerHTML = "";
      addMedicationEntry(); // Reset with one empty row
    } catch (err) {
      alert("Error: " + err.message);
    }
  });

  // Add initial empty entry
  addMedicationEntry();

  // Attach listener to search form
  const searchForm = document.getElementById("searchForm");
  if (searchForm) {
    searchForm.addEventListener("submit", async function (e) {
      e.preventDefault();
      const name = document.getElementById("searchName").value.trim();

      try {
        const res = await fetch(`/api/medications/patient-name/${encodeURIComponent(name)}`);
        if (!res.ok) throw new Error("No data found");

        const data = await res.json();
        const resultDiv = document.getElementById("medicationResults");

        if (data.length === 0) {
          resultDiv.innerHTML = "<p style='color:red;'>No medications found for that patient.</p>";
          return;
        }

        let html = `
          <h4 style="margin-bottom: 15px;">Medications for <b>${name}</b></h4>
          <table>
            <tr>
              <th>Medicine</th>
              <th>Dosage</th>
              <th>Frequency</th>
              <th>Duration</th>
              <th>Start Date</th>
              <th>End Date</th>
            </tr>
        `;

        const today = new Date();
        const startDateStr = today.toISOString().split("T")[0];

        data.forEach(m => {
          const durationDays = parseInt(m.duration);
          let endDateStr = "N/A";

          if (!isNaN(durationDays)) {
            const endDate = new Date(today);
            endDate.setDate(today.getDate() + durationDays);
            endDateStr = endDate.toISOString().split("T")[0];
          }

          html += `
            <tr>
              <td>${m.medicineName}</td>
              <td>${m.dosage}</td>
              <td>${m.frequency}</td>
              <td>${m.duration}</td>
              <td>${startDateStr}</td>
              <td>${endDateStr}</td>
            </tr>
          `;
        });

        html += `</table>`;
        resultDiv.innerHTML = html;
      } catch (err) {
        document.getElementById("medicationResults").innerHTML = `<p style='color:red;'>${err.message}</p>`;
      }
    });
  }
});

// Add new medication input block
function addMedicationEntry() {
  const container = document.getElementById("medicationList");
  const entry = document.createElement("div");
  entry.classList.add("medication-entry");
  entry.innerHTML = `
    <input type="text" placeholder="Medicine Name" class="medicineName" required />
    <input type="text" placeholder="Dosage" class="dosage" required />
    <input type="text" placeholder="Frequency" class="frequency" required />
    <input type="text" placeholder="Duration" class="duration" required />
    <button type="button" class="remove-button" onclick="removeEntry(this)">Remove</button>
  `;
  container.appendChild(entry);
}

// Remove medication input block
function removeEntry(button) {
  button.parentElement.remove();
}
