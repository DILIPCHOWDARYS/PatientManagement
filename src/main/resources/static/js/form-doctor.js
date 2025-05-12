document.addEventListener("DOMContentLoaded", function () {
  const form = document.getElementById("doctorForm");

  form.addEventListener("submit", async function (event) {
    event.preventDefault();

    const name = document.getElementById("doctorName").value.trim();
    const email = document.getElementById("doctorEmail").value.trim();
    const specialization = document.getElementById("specialization").value.trim();

    if (!name || !email || !specialization) {
      alert("All fields are required.");
      return;
    }

    const doctorData = { name, email, specialization };

    try {
      const response = await fetch("/api/doctors", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(doctorData)
      });

      if (response.ok) {
        alert("Doctor added successfully!");
        form.reset();
        window.location.href = "/doctors"; // or dashboard
      } else {
        alert("Failed to add doctor.");
      }
    } catch (error) {
      alert("Error: " + error.message);
    }
  });
});
