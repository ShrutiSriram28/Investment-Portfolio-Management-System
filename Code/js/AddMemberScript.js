function check_availability() {
    var xhttp;
    var v=document.getElementById("username").value;  
    var url="java/GetMember?username="+v;  
    xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {
            var val=xhttp.responseText;  
            document.getElementById('validate_member').innerHTML=val;  
        }
    };
    xhttp.open("GET",url,true);
    xhttp.send();
}