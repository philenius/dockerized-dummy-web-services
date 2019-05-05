$( document ).ready(function() {

    $('button.thumbs-up, button.thumbs-down').click(function () {
        var thisIcon = $(this).find('i');
        if (thisIcon.hasClass('fas')) {
            return;
        }

        thisIcon.addClass('fas').removeClass('far');
        var counter = $(this).parent().find('span.counter');

        if ($(this).hasClass('thumbs-down')) {
            var otherIcon = $(this).parent().find('i.fa-thumbs-up');
            otherIcon.addClass('far');
            otherIcon.removeClass('fas');
            counter.text(parseInt(counter.text()) - 1);
        } else {
            var otherIcon = $(this).parent().find('i.fa-thumbs-down');
            otherIcon.addClass('far');
            otherIcon.removeClass('fas');
            counter.text(parseInt(counter.text()) + 1);
        }
    });

});
