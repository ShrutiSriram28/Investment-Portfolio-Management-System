function autofill_scheme() {
    var xhttp;
    var v=document.getElementById("scheme").value;  
    var url="java/GetMFScheme?scheme="+v;  
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

function fetch_NAV() {
    var xhttp;
    var v=document.getElementById("scheme").value;  
    var url="java/GetMFNAV?scheme="+v;  
    xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            var val=xhttp.responseText;  
            document.getElementById('nav_hidden').innerHTML=val;
            set_nav();  
        }
    };
    xhttp.open("GET",url,true);
    xhttp.send();
}

function set_nav() {
    document.getElementById('nav').value = document.getElementById('hidden_nav').value;
}

function cancel() {
    document.getElementById("scheme").value="";
    document.getElementById("nav").value="";
    document.getElementById("purchase_nav").value="";
    document.getElementById("amount").value="";
    document.getElementById("units").value="";
}

function validate_nav() {
    var purchase_nav = parseInt(document.getElementById("purchase_nav").value);
    if(purchase_nav < 0)
    {
        document.getElementById("validate_purchase_nav").innerHTML = "NAV cannot be negative<br><br>";
        document.getElementById("purchase_nav").value = "";
    }
    else
    {
        document.getElementById("validate_purchase_nav").innerHTML = "<br><br>";
    }
}

function calculate_units() {
    var purchase_nav = parseFloat(document.getElementById("purchase_nav").value);
    var amount = parseFloat(document.getElementById("amount").value);
    document.getElementById("units").value = amount / purchase_nav;
}