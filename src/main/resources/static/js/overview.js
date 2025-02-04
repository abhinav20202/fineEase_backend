document.addEventListener("DOMContentLoaded", function () {
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