$.ajax({
type: 'POST',
url: '/api/subscriptions',
dataType: 'json',
success: function(response){
    var name = response;
    var yourTableHTML = "";
        jQuery.each(name, function(i,data) {
            $('#subscriptionTable').append('<tr><td>' + data.accountId + '</td><td>' + data.creatorFirstName + '</td><td>' + data.creatorLastName + '</td><td>' + data.edition + '</td></tr>');
        });
}
});