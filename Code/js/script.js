function autofill_scheme() {
    var xhttp;
    var v=document.getElementById("scheme").value;  
    var url="java/GetScheme?scheme="+v;  
    xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            var val=xhttp.responseText;  
            document.getElementById('scheme_list').innerHTML=val;  
        }
    };
    xhttp.open("GET",url,true);
    xhttp.send();
}

function fetch_price() {
    var xhttp;
    var v=document.getElementById("scheme").value;  
    var url="java/GetStockPrice?scheme="+v;  
    xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            var val=xhttp.responseText;  
            document.getElementById('price_hidden').innerHTML=val;
            set_price();  
        }
    };
    xhttp.open("GET",url,true);
    xhttp.send();
}

function set_price() {
    document.getElementById('price').value = document.getElementById('hidden_price').value;
}

function reset() {
    document.getElementById("scheme").value="";
    document.getElementById("price").value="";
    document.getElementById("purchase_price").value="";
    document.getElementById("quantity").value="";
    document.getElementById("net").value="";
}

function cancel() {
    reset();
}

function calculate_net_amount() {
    var quantity = parseInt(document.getElementById("quantity").value);
    if(quantity < 0)
    {
        document.getElementById("validate_quantity").innerHTML = "Quantity cannot be negative<br><br>";
    }
    else
    {
        var price = parseInt(document.getElementById("purchase_price").value);
        document.getElementById("net").value = price * quantity;
        document.getElementById("validate_quantity").innerHTML = "<br><br>";
    }
}