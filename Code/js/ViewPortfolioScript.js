var click = 0;

function slide() {
    if(click == 0)
    {
        document.getElementById("side_panel").style.width = "25vw";
        click = 1;
        document.getElementById('chat').style.display = "block";
        document.getElementById('stocks').style.display = "block";
        document.getElementById('mf').style.display = "block";
        document.getElementById('bullion').style.display = "block";
        document.getElementById('logout').style.display = "block";
    }
    else 
    {
        document.getElementById("side_panel").style.width = "7vw";
        click = 0;
        document.getElementById('chat').style.display = "none";
        document.getElementById('stocks').style.display = "none";
        document.getElementById('mf').style.display = "none";
        document.getElementById('bullion').style.display = "none";
        document.getElementById('logout').style.display = "none";
    }
}