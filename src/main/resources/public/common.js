$('.menu .item').tab();

$.ajax({
    type: 'GET',
    url: '/api/user/current',
    dataType: 'json',
    success: function(user){
        $('#currentUser').html(user.firstname + " " + user.lastname).attr('title', user.email);
        $('#logoutButton').removeClass("disabled").attr('href', '/logout');
    },
    error: function (xhr, ajaxOptions, thrownError) {
        $('#currentUser').html("Anonymous");
    }
});