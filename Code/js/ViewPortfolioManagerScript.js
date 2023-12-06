var click = 0;

function slide() {
    if(click == 0)
    {
        document.getElementById("side_panel").style.width = "25vw";
        click = 1;
        document.getElementById('group').style.display = "block";
        document.getElementById('logout').style.display = "block";
    }
    else 
    {
        document.getElementById("side_panel").style.width = "7vw";
        click = 0;
        document.getElementById('group').style.display = "none";
        document.getElementById('logout').style.display = "none";
    }
}