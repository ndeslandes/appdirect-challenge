$('.menu .item').tab();

$.ajax({
    type: 'GET',
    url: '/api/subscriptions',
    dataType: 'json',
    success: function(subscriptions){
        jQuery.each(subscriptions, function(i, subscription) {
            var usersTable = '';
            jQuery.each(subscription.users, function(i, user) {
                if (usersTable != '') usersTable += '<br>';
                usersTable += user.firstname + " " + user.lastname;
            });
            $('#subscriptionTable').append('<tr><td>' + subscription.id + '</td><td>' + subscription.companyName + '</td><td>' + subscription.edition + '</td><td>' + $.trim(subscription.status) + '</td><td>' + usersTable + '</td></tr>');
        });
    }
});

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