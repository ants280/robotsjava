// This script will make the screen as big as possible
x = 840;
y = 850;

if(x > parseInt(screen.width) && x <= parseInt(screen.availWidth))
{
 top.window.resizeTo(x, parseInt(screen.height));
}

if(y > parseInt(screen.height) && y <= parseInt(screen.availHeight))
{
 top.window.resizeTo(screen.width, y);
}

top.window.resizeTo(screen.availWidth, screen.availHeight);
