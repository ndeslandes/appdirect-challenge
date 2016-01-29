$.ajax({
type: 'POST',
url: '/api/users',
dataType: 'json',
success: function(response){
    var name = response;
    var yourTableHTML = "";
        jQuery.each(name, function(i,data) {
            $('#userTable').append('<tr><td>' + data.id + '</td><td>' + data.firstName + '</td><td>' + data.lastName + '</td><td>' + data.email + '</td><td>' + data.language + '</td></tr>');
        });
}
});