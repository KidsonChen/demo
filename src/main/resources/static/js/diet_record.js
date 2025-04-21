let foodOptions = [
  "米飯",
  "麵條",
  "青菜",
  "雞肉",
  "牛肉",
  "豬肉",
  "魚",
  "豆腐",
  "蛋",
];
let portionOptions = [
  "0.5碗",
  "1碗",
  "1.5碗",
  "2碗",
  "50克",
  "100克",
  "150克",
  "200克",
];

function addFoodItem(containerId) {
  const container = document.getElementById(containerId);
  const foodItem = document.createElement("div");
  foodItem.className = "food-item";

  const foodSelect = document.createElement("select");
  foodSelect.name = `${containerId}-food[]`;
  foodOptions.forEach((option) => {
    const optionElement = document.createElement("option");
    optionElement.value = option;
    optionElement.textContent = option;
    foodSelect.appendChild(optionElement);
  });

  const portionSelect = document.createElement("select");
  portionSelect.name = `${containerId}-portion[]`;
  portionOptions.forEach((option) => {
    const optionElement = document.createElement("option");
    optionElement.value = option;
    optionElement.textContent = option;
    portionSelect.appendChild(optionElement);
  });

  const removeButton = document.createElement("button");
  removeButton.textContent = "刪除";
  removeButton.type = "button";
  removeButton.onclick = function () {
    container.removeChild(foodItem);
  };

  foodItem.appendChild(foodSelect);
  foodItem.appendChild(portionSelect);
  foodItem.appendChild(removeButton);
  container.appendChild(foodItem);
}

document.getElementById("addFoodBtn").addEventListener("click", () => {
  const newFood = prompt("請輸入新的食物名稱:");
  if (newFood && !foodOptions.includes(newFood)) {
    foodOptions.push(newFood);
    alert(`已添加新食物: ${newFood}`);
  }
});

document.getElementById("dietForm").addEventListener("submit", async (e) => {
  e.preventDefault();
  const formData = new FormData(e.target);
  const dietRecord = {
    breakfast: {
      time: formData.get("breakfast-time"),
      items: getItems(formData, "breakfastItems"),
    },
    lunch: {
      time: formData.get("lunch-time"),
      items: getItems(formData, "lunchItems"),
    },
    dinner: {
      time: formData.get("dinner-time"),
      items: getItems(formData, "dinnerItems"),
    },
    snacks: getItems(formData, "snackItems"),
    fruits: getItems(formData, "fruitItems"),
  };

  try {
    const response = await fetch("/api/diet-record", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(dietRecord),
    });

    if (response.ok) {
      alert("飲食記錄已成功提交!");
      e.target.reset();
    } else {
      alert("提交失敗,請稍後再試。");
    }
  } catch (error) {
    console.error("提交時發生錯誤:", error);
    alert("提交時發生錯誤,請稍後再試。");
  }
});

function getItems(formData, containerId) {
  const foods = formData.getAll(`${containerId}-food[]`);
  const portions = formData.getAll(`${containerId}-portion[]`);
  return foods.map((food, index) => ({
    food: food,
    portion: portions[index],
  }));
}

// 初始化每個餐次的食物項
[
  "breakfastItems",
  "lunchItems",
  "dinnerItems",
  "snackItems",
  "fruitItems",
].forEach((containerId) => {
  addFoodItem(containerId);
});
