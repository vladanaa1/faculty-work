const appetizers = [
    {
        id: 1,
        name: 'Prolećne rolnice',
        description: 'Hrskave rolnice punjene svežim povrćem i mesom.',
        priceSmall: 300,
        priceLarge: 500,
        imageUrl: '../images/prolecne-rolnice.jpg',
        ratings: [4, 5, 5],
        type: "appetizers"
    },
    {
        id: 2,
        name: 'Krompirići',
        description: 'Hrskavi prženi krompirići.',
        priceSmall: 200,
        priceLarge: 350,
        imageUrl: '../images/krompirici.jpg',
        ratings: [5, 4, 3],
        type: "appetizers"
    },
    {
        id: 3,
        name: 'Pohovani luk',
        description: 'Hrskavi prstenovi pohovanog luka.',
        priceSmall: 250,
        priceLarge: 400,
        imageUrl: '../images/pohovani-luk.jpg',
        ratings: [4, 4, 4],
        type: "appetizers"
    },
    {
        id: 4,
        name: 'Nudle sa pečurkama i povrćem',
        description: 'Nudle sa domaćim rezancima, svežim pečurkama i povrćem.',
        priceSmall: 800,
        priceLarge: 1200,
        imageUrl: '../images/nudle-pecurke-povrce.jpg',
        ratings: [5, 5, 4],
        type: "main"
    },
    {
        id: 5,
        name: 'Piletina sa povrćem, kikirikijem i soja sosom.',
        description: 'Obrok prebogat proteinima, spremljen na tradicionalni kineski način.',
        priceSmall: 850,
        priceLarge: 1250,
        imageUrl: '../images/piletina-povrce-kikiriki-soja.jpg',
        ratings: [5, 5, 5],
        type: "main"
    },
    {
        id: 6,
        name: 'Karamelizovana piletina sa rižom',
        description: 'Za ljubitelje riže, spremljene na tradicionalni kineski način.',
        priceSmall: 900,
        priceLarge: 1300,
        imageUrl: '../images/karamelizovana-piletina-riza.jpg',
        ratings: [4, 3, 4],
        type: "main"
    },
    {
        id: 7,
        name: 'Pohovani sladoled',
        description: 'Hrskavo-slatki sladoled od vanile.',
        priceSmall: 300,
        priceLarge: 400,
        imageUrl: '../images/pohovani-sladoled.jpg',
        ratings: [5, 5, 4],
        type: "desert"
    },
    {
        id: 8,
        name: 'Pohovana čokolada',
        description: 'Najfinija pohovana mlečna čokolada za sva Vaša čula!',
        priceSmall: 400,
        priceLarge: 550,
        imageUrl: '../images/pohovana-cokolada.jpg',
        ratings: [5, 5, 5],
        type: "desert"
    },
    {
        id: 9,
        name: 'Rolnice sa bananom',
        description: 'Tradicionalne rolnice sa bananom.',
        priceSmall: 400,
        priceLarge: 480,
        imageUrl: '../images/rolnice-sa-bananom.jpg',
        ratings: [4, 3, 4],
        type: "desert"
    }
];

const cart = JSON.parse(localStorage.getItem('cart')) || [];
const orders = JSON.parse(localStorage.getItem('orders')) || [];

document.addEventListener('DOMContentLoaded', initializePage);

function initializePage() {
    const path = window.location.pathname;
    if (path.includes('predjela.html')) {
        displayDishes("appetizers");
        document.getElementById('sortAppetizers').addEventListener('change', function() {
            sortDishes("appetizers");
        });
        document.getElementById('searchAppetizers').addEventListener('input', function() {
            filterDishes("appetizers");
        });
    } else if (path.includes('my-account.html')) {
        displayCartItems();
        displayOrderHistory();
    } else if (path.includes('details.html')) {
        const urlParams = new URLSearchParams(window.location.search);
        const dishId = parseInt(urlParams.get('id'));
        displayDishDetails(dishId);
    } else if (path.includes('glavna-jela.html')) {
        displayDishes("main");
        document.getElementById('sortAppetizers').addEventListener('change', function() {
            sortDishes("main");
        });
        document.getElementById('searchAppetizers').addEventListener('input', function() {
            filterDishes("main");
        });
    } else if (path.includes('desert.html')) {
        displayDishes("desert");
        document.getElementById('sortAppetizers').addEventListener('change', function() {
            sortDishes("desert");
        });
        document.getElementById('searchAppetizers').addEventListener('input', function() {
            filterDishes("desert");
        });
    }
    document.getElementById('downloadMenuBtn')?.addEventListener('click', downloadMenu);
    displayTopRatedDishes();
}

function displayDishes(type) {
    const dishList = document.getElementById('appetizersList');
    dishList.innerHTML = '';
    appetizers.forEach(dish => {
        if (dish.type === type) {
            dishList.innerHTML += `
                <div class="col-md-4 mb-4">
                    <div class="card">
                        <img src="${dish.imageUrl}" class="card-img-top" alt="${dish.name}">
                        <div class="card-body">
                            <h5 class="card-title">${dish.name}</h5>
                            <p class="card-text">${dish.description}</p>
                            <p>Mala porcija: ${dish.priceSmall} RSD</p>
                            <p>Velika porcija: ${dish.priceLarge} RSD</p>
                            <input type="radio" id="small${dish.id}" name="portion${dish.id}" value="small"> Mala porcija
                            <input type="radio" id="large${dish.id}" name="portion${dish.id}" value="large" checked> Velika porcija
                            <br><br>
                            <button class="btn btn-custom mb-2" onclick="addToCart(${dish.id}, getSelectedPortion(${dish.id}))">Dodaj u korpu</button>
                            <br>
                            <a href="details.html?id=${dish.id}" class="btn btn-custom">Detalji</a>
                        </div>
                    </div>
                </div>
            `;
        }
    });
}

function getSelectedPortion(dishId) {
    const smallRadio = document.getElementById(`small${dishId}`);
    return smallRadio.checked ? 'small' : 'large';
}

function addToCart(id, size) {
    const dish = appetizers.find(dish => dish.id === id);
    const cartItem = cart.find(item => item.id === id && item.size === size);
    if (cartItem) {
        cartItem.quantity++;
    } else {
        cart.push({ ...dish, size, quantity: 1 });
    }
    localStorage.setItem('cart', JSON.stringify(cart));
    alert('Dodato u korpu!');
}

function filterDishes(type) {
    const searchInput = document.getElementById('searchAppetizers').value.toLowerCase();
    const filteredDishes = appetizers.filter(dish =>
        dish.type === type && dish.name.toLowerCase().includes(searchInput)
    );
    displayFilteredDishes(filteredDishes, type);
}

function displayFilteredDishes(filteredDishes, type) {
    const dishList = document.getElementById('appetizersList');
    dishList.innerHTML = '';
    filteredDishes.forEach(dish => {
        dishList.innerHTML += `
            <div class="col-md-4 mb-4">
                <div class="card">
                    <img src="${dish.imageUrl}" class="card-img-top" alt="${dish.name}">
                    <div class="card-body">
                        <h5 class="card-title">${dish.name}</h5>
                        <p class="card-text">${dish.description}</p>
                        <p>Mala porcija: ${dish.priceSmall} RSD</p>
                        <p>Velika porcija: ${dish.priceLarge} RSD</p>
                        <input type="radio" id="small${dish.id}" name="portion${dish.id}" value="small"> Mala porcija
                        <input type="radio" id="large${dish.id}" name="portion${dish.id}" value="large" checked> Velika porcija
                        <br><br>
                        <button class="btn btn-custom mb-2" onclick="addToCart(${dish.id}, getSelectedPortion(${dish.id}))">Dodaj u korpu</button>
                        <br>
                        <a href="details.html?id=${dish.id}" class="btn btn-custom">Detalji</a>
                    </div>
                </div>
            </div>
        `;
    });
}

function sortDishes(type) {
    const sortValue = document.getElementById('sortAppetizers').value;
    let sortedDishes = appetizers.filter(dish => dish.type === type);

    if (sortValue === 'priceAsc') {
        sortedDishes.sort((a, b) => a.priceSmall - b.priceSmall);
    } else if (sortValue === 'priceDesc') {
        sortedDishes.sort((a, b) => b.priceSmall - a.priceSmall);
    } else if (sortValue === 'rating') {
        sortedDishes.sort((a, b) => {
            const avgRatingA = a.ratings.reduce((sum, rating) => sum + rating, 0) / a.ratings.length;
            const avgRatingB = b.ratings.reduce((sum, rating) => sum + rating, 0) / b.ratings.length;
            return avgRatingB - avgRatingA;
        });
    }

    displayFilteredDishes(sortedDishes, type);
}

function displayCartItems() {
    const cartItemsContainer = document.getElementById('cartItems');
    cartItemsContainer.innerHTML = '';

    cart.forEach((item, index) => {
        const price = item.size === 'small' ? item.priceSmall : item.priceLarge;
        cartItemsContainer.innerHTML += `
            <div class="cart-item">
                <div>
                    <h5>${item.name}</h5>
                    <p>${item.size === 'small' ? 'Mala porcija' : 'Velika porcija'} - ${price} RSD</p>
                    <p>Količina: 
                        <input type="number" min="1" value="${item.quantity}" onchange="updateCartItemQuantity(${index}, this.value)">
                    </p>
                    <p>Promeni veličinu: 
                        <select onchange="updateCartItemSize(${index}, this.value)">
                            <option value="small" ${item.size === 'small' ? 'selected' : ''}>Mala</option>
                            <option value="large" ${item.size === 'large' ? 'selected' : ''}>Velika</option>
                        </select>
                    </p>
                    <button class="btn btn-danger" onclick="removeFromCart(${index})">Ukloni</button>
                </div>
            </div>
        `;
    });

    const total = cart.reduce((sum, item) => {
        const price = item.size === 'small' ? item.priceSmall : item.priceLarge;
        return sum + price * item.quantity;
    }, 0);

    document.getElementById('cartTotal').innerText = `Ukupno: ${total} RSD`;
}


function updateCartItemSize(index, newSize) {
    cart[index].size = newSize;
    localStorage.setItem('cart', JSON.stringify(cart));
    displayOrderHistory();
    displayCartItems();
}


function updateCartItemQuantity(index, newQuantity) {
    cart[index].quantity = parseInt(newQuantity);
    localStorage.setItem('cart', JSON.stringify(cart));
    displayCartItems();
}

function removeFromCart(index) {
    cart.splice(index, 1);
    localStorage.setItem('cart', JSON.stringify(cart));
    displayCartItems();
}

function displayOrderHistory() {
    const ordersContainer = document.getElementById('orderHistory');
    ordersContainer.innerHTML = '';

    orders.forEach(order => {
        ordersContainer.innerHTML += `
            <div class="order-item">
                <h5>Porudžbina - ${new Date(order.date).toLocaleDateString()}</h5>
                <ul>
                    ${order.items.map(item => `
                        <li>${item.name} (${item.size === 'small' ? 'Mala' : 'Velika'} porcija) - ${item.quantity} x ${item.size === 'small' ? item.priceSmall : item.priceLarge} RSD</li>
                    `).join('')}
                </ul>
                <p>Ukupno: ${order.total} RSD</p>
            </div>
        `;
    });
}

function placeOrder() {
    if (cart.length === 0) {
        alert('Korpa je prazna!');
        return;
    }

    const total = cart.reduce((sum, item) => {
        const price = item.size === 'small' ? item.priceSmall : item.priceLarge;
        return sum + price * item.quantity;
    }, 0);

    orders.push({
        date: new Date().toISOString(),
        items: cart,
        total: total
    });

    localStorage.setItem('orders', JSON.stringify(orders));
    cart.length = 0;
    localStorage.setItem('cart', JSON.stringify(cart));
    displayCartItems();
    alert('Porudžbina je uspešno kreirana!');
}

function displayDishDetails(dishId) {
    const dish = appetizers.find(dish => dish.id === dishId);
    if (!dish) {
        document.getElementById('dishDetails').innerHTML = '<p>Jelo nije pronađeno!</p>';
        return;
    }

    document.getElementById('dishDetails').innerHTML = `
        <img src="${dish.imageUrl}" alt="${dish.name}">
        <h1>${dish.name}</h1>
        <p>${dish.description}</p>
        <p>Mala porcija: ${dish.priceSmall} RSD</p>
        <p>Velika porcija: ${dish.priceLarge} RSD</p>
        <input type="radio" id="small${dish.id}" name="portion${dish.id}" value="small"> Mala porcija
        <input type="radio" id="large${dish.id}" name="portion${dish.id}" value="large" checked> Velika porcija
        <br><br>
        <button class="btn btn-custom mb-2" onclick="addToCart(${dish.id}, getSelectedPortion(${dish.id}))">Dodaj u korpu</button>
    `;
}

function convertToLatin(text) {
    const latinMap = {
        'č': 'c', 'ć': 'c', 'š': 's', 'ž': 'z', 'đ': 'dj', 'Č': 'C', 'Ć': 'C', 'Š': 'S', 'Ž': 'Z', 'Đ': 'DJ'
    };

    return text.replace(/[čćšžđČĆŠŽĐ]/g, function(match) {
        return latinMap[match];
    });
}

function downloadMenu() {
    const { jsPDF } = window.jspdf;
    const doc = new jsPDF();

    doc.setFont('helvetica');
    doc.setFontSize(14); 

    const menuTitle = 'Meni "Panda Express" 2024, jul';
    const textWidth = doc.getStringUnitWidth(menuTitle) * doc.internal.getFontSize() / doc.internal.scaleFactor;
    const textX = (doc.internal.pageSize.getWidth() - textWidth) / 2;
    let textY = 20;
    doc.text(menuTitle, textX, textY);

    let yPosition = textY + 10;

    appetizers.forEach((appetizer, index) => {
        const latinName = convertToLatin(appetizer.name);

        doc.text(`${index + 1}. ${latinName}`, 10, yPosition);
        yPosition += 7;
        doc.text(`Mala porcija: ${appetizer.priceSmall} RSD`, 15, yPosition);
        yPosition += 5;
        doc.text(`Velika porcija: ${appetizer.priceLarge} RSD`, 15, yPosition);
        yPosition += 12; 

        if (yPosition > doc.internal.pageSize.getHeight() - 20) {
            doc.addPage();
            yPosition = 20; 
        }
    });

    doc.save('jelovnik.pdf');
}

function displayTopRatedDishes() {
    const topRatedContainer = document.getElementById('topRatedDishes');
    if (!topRatedContainer) return;

    const topRatedDishes = appetizers.slice().sort((a, b) => {
        const avgRatingA = a.ratings.reduce((sum, rating) => sum + rating, 0) / a.ratings.length;
        const avgRatingB = b.ratings.reduce((sum, rating) => sum + rating, 0) / b.ratings.length;
        return avgRatingB - avgRatingA;
    }).slice(0, 3);

    topRatedDishes.forEach(dish => {
        topRatedContainer.innerHTML += `
            <div class="col-md-4 mb-4">
                <div class="card">
                    <img src="${dish.imageUrl.replace('../', '')}" class="card-img-top" alt="${dish.name}">
                    <div class="card-body">
                        <h5 class="card-title">${dish.name}</h5>
                        <p class="card-text">${dish.description}</p>
                        <p>Mala porcija: ${dish.priceSmall} RSD</p>
                        <p>Velika porcija: ${dish.priceLarge} RSD</p>
                        <input type="radio" id="small${dish.id}" name="portion${dish.id}" value="small"> Mala porcija
                        <input type="radio" id="large${dish.id}" name="portion${dish.id}" value="large" checked> Velika porcija
                        <br><br>
                        <button class="btn btn-custom mb-2" onclick="addToCart(${dish.id}, getSelectedPortion(${dish.id}))">Dodaj u korpu</button>
                        <br>
                        <a href="details.html?id=${dish.id}" class="btn btn-custom">Detalji</a>
                    </div>
                </div>
            </div>
        `;
    });
}