/*document.addEventListener("DOMContentLoaded", function () {
    let expenseElement = document.getElementById("expenseData");

    if (expenseElement) {
        let jsonData = expenseElement.getAttribute("data-expense");

        try {
			
		//	let jsonString = JSON.stringify(jsonData);
            let expenseData = JSON.parse(jsonData);

            console.log("Parsed Expense Data:", expenseData); // Debugging

            if (Array.isArray(expenseData)) {
                let categories = expenseData.map(item => item.expenseCategory);
                let expenses = expenseData.map(item => parseFloat(item.categoryExpense));

                let ctx = document.getElementById("categoriesChart").getContext("2d");
                new Chart(ctx, {
                    type: "pie",
                    data: {
                        labels: categories,
                        datasets: [{
                            data: expenses,
                            backgroundColor: ["#FF5733", "#33FF57", "#3357FF", "#FF33A1"]
                        }]
                    }
                });
            } else {
                console.error("Invalid JSON format:", expenseData);
            }
        } catch (error) {
            console.error("Error parsing expense data:", error);
        }
    } else {
        console.error("Expense data element not found!");
    }
});

*/


document.addEventListener("DOMContentLoaded", function () {
    renderPieChart();
    renderLineChart();
});

function renderPieChart() {
    let expenseElement = document.getElementById("expenseData");

    if (expenseElement) {
        let jsonData = expenseElement.getAttribute("data-expense");

        try {
            let expenseData = JSON.parse(jsonData);

            if (Array.isArray(expenseData)) {
                let categories = expenseData.map(item => item.expenseCategory);
                let expenses = expenseData.map(item => parseFloat(item.categoryExpense));

                let ctx = document.getElementById("categoriesChart").getContext("2d");
                new Chart(ctx, {
                    type: "pie",
                    data: {
                        labels: categories,
                        datasets: [{
                            data: expenses,
                            backgroundColor: ["#FF5733", "#33FF57", "#3357FF", "#FF33A1"]
                        }]
                    }
                });
            } else {
                console.error("Invalid JSON format:", expenseData);
            }
        } catch (error) {
            console.error("Error parsing expense data:", error);
        }
    } else {
        console.error("Expense data element not found!");
    }
}

function renderLineChart() {
    let dailyIncomeElement = document.getElementById("dailyIncomeData");
    let dailyExpenseElement = document.getElementById("dailyExpenseData");

    if (!dailyIncomeElement || !dailyExpenseElement) {
        console.error("Chart data elements not found. Make sure they exist in the HTML.");
        return;
    }

    try {
        // Extract JSON data safely from dataset
        let incomeJson = dailyIncomeElement.getAttribute("data-income") || "[]";
        let expenseJson = dailyExpenseElement.getAttribute("data-expense") || "[]";

        // Parse JSON data
        let dailyIncomeData = JSON.parse(incomeJson);
        let dailyExpenseData = JSON.parse(expenseJson);

        console.log("Parsed Daily Income Data:", dailyIncomeData);
        console.log("Parsed Daily Expense Data:", dailyExpenseData);

        // Convert lists into maps with correct field names
        let incomeMap = new Map(dailyIncomeData.map(item => [item.incomeDate, item.dailyIncome]));
        let expenseMap = new Map(dailyExpenseData.map(item => [item.expenseDate, item.dailyExpense]));

        // Get all unique dates from both datasets
        let allDates = new Set([...incomeMap.keys(), ...expenseMap.keys()]);

        // Convert Set into sorted array (ascending order)
        let sortedDates = Array.from(allDates).sort();

        // Prepare final data lists
        let labels = [];
        let incomeValues = [];
        let expenseValues = [];

        sortedDates.forEach(date => {
            labels.push(date);
            incomeValues.push(incomeMap.get(date) || 0); // Default to 0 if missing
            expenseValues.push(expenseMap.get(date) || 0); // Default to 0 if missing
        });

        // Get chart context
        let ctx = document.getElementById("lineChart").getContext("2d");

        // Destroy previous chart instance if exists
        if (window.lineChartInstance) {
            window.lineChartInstance.destroy();
        }

        // Create Line Chart using Chart.js
        window.lineChartInstance = new Chart(ctx, {
            type: "line",
            data: {
                labels: labels, // X-Axis: Dates
                datasets: [
                    {
                        label: "Daily Income",
                        data: incomeValues,
                        borderColor: "green",
                        backgroundColor: "rgba(0, 128, 0, 0.1)", 
                        fill: true,
                        tension: 0.4 // Smooth curves
                    },
                    {
                        label: "Daily Expense",
                        data: expenseValues,
                        borderColor: "red",
                        backgroundColor: "rgba(255, 0, 0, 0.1)", 
                        fill: true,
                        tension: 0.4 // Smooth curves
                    }
                ]
            },
            options: {
                responsive: true,
                maintainAspectRatio: false,
                plugins: {
                    legend: {
                        display: true,
                        position: "top"
                    }
                },
                scales: {
                    x: { 
                        title: { display: true, text: "Date" },
                        ticks: { autoSkip: true, maxTicksLimit: 10 }
                    },
                    y: { 
                        title: { display: true, text: "Amount" },
                        beginAtZero: true 
                    }
                }
            }
        });

    } catch (error) {
        console.error("Error parsing JSON data:", error);
    }
}
