document.addEventListener("DOMContentLoaded", function () {
  const form = document.getElementById("appointmentForm");

  form.addEventListener("submit", async function (event) {
    event.preventDefault();

    const doctorId = document.getElementById("doctorId").value.trim();
    const date = document.getElementById("date").value;
    const time = document.getElementById("time").value;
    const reason = document.getElementById("reason").value.trim();

    const today = new Date().toISOString().split("T")[0];

    // Basic validation
    if (!doctorId) {
      alert("Doctor ID is required.");
      return;
    }
    if (!date || date < today) {
      alert("Please select a valid date.");
      return;
    }
    if (!time) {
      alert("Time is required.");
      return;
    }

    // Prepare data
    const appointmentData = { doctorId, date, time, reason };

    try {
      const response = await fetch("/api/appointments", {
        method: "POST",
        headers: {
          "Content-Type": "application/json"
        },
        body: JSON.stringify(appointmentData)
      });

      if (response.ok) {
        alert("Appointment booked successfully!");
        form.reset();
      } else {
        alert("Booking failed. Please try again.");
      }
    } catch (error) {
      alert("Error: " + error.message);
    }
  });
});
