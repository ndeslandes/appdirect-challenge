$.ajax({
type: 'GET',
url: '/api/subscriptions',
dataType: 'json',
success: function(response){
    var name = response;
    var yourTableHTML = '';
    jQuery.each(name, function(i,data) {
        $('#subscriptionTable').append('<tr><td>' + data.id + '</td><td>' + data.companyName + '</td><td>' + data.edition + '</td><td>' + data.status + '</td></tr>');
    });
}
});

$.ajax({
type: 'GET',
url: '/api/user/current',
dataType: 'json',
success: function(user){
    $('#currentUser').html(user.firstname + " " + user.lastname).attr('title', user.email)
}
});