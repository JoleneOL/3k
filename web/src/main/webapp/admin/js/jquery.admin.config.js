/**
 * Created by allan on 1/13/16.
 */

$(function () {
    //初始化页面
    configHandler.pageInit();

    $("input[name=chkOnlyInvite]").change(function () {
        var onlyInvite = false;
        if ($(this).attr("checked")) {
            onlyInvite = true;
        }
        $("input[name=onlyInvite]").val(onlyInvite);
    });

    $("button[class~=btn-primary]").click(function () {
        configHandler.setInterestReward();

        $("#configForm").submit();
    });
});

var configHandler = {
    pageInit: function () {
        // Tags Input
        jQuery('#tags').tagsInput({width: 'auto'});

        //富文本初始化
        jQuery('textarea[name=userHelpMessage]').wysihtml5({color: true, html: true});
        jQuery('textarea[name=indexTopNotice]').wysihtml5({color: true, html: true});
        jQuery('textarea[name=indexBottomNotice]').wysihtml5({color: true, html: true});
        $('textarea[name=regWelcomeMessage]').wysihtml5({color: true, html: true});

        //表单验证初始化
        $("#configForm").validate({
            ignoreTitle: true,
            highlight: function (element) {
                $(element).closest('.form-group').removeClass('has-success').addClass('has-error');
            },
            success: function (element) {
                $(element).closest('.form-group').removeClass('has-error');
                $(element).remove();
            },
            onfocusout: function (element) {
                $(element).valid();
            },
            rules: {
                title: "required",
                url: {
                    required: true,
                    url: true
                },
                stock: {
                    required: true,
                    digits: true,
                    isHundred: true
                },
                maxOrders: commonRules.rangeDigits(1, null),
                maxLots: commonRules.rangeDigits(1, null),
                queueDays: {
                    required: true,
                    digits: true
                },
                rate: commonRules.rangeNumber(0, 1),
                maxOperateHours: commonRules.rangeDigits(1, null),
                inspectStartDayOfMonth: {
                    required: true,
                    digits: true,
                    min: 1,
                    minTo: '#inspectEndDayOfMonth'
                },
                inspectEndDayOfMonth: {
                    required: true,
                    digits: true,
                    maxTo: '#inspectStartDayOfMonth'
                },
                directRewardRate: commonRules.rangeNumber(0, 1),
                directRewardRate2: commonRules.rangeNumber(0, 1),
                leaderStopRecommends: commonRules.rangeDigits(null, null),
                leaderStopMembers: commonRules.rangeDigits(null, null),
                leaderStopInvests: commonRules.rangeDigits(null, null),
                leaderRate: commonRules.rangeNumber(0, 1)
            },
            messages: {
                inspectStartDayOfMonth: {
                    minTo: "输入的值必须小于后者"
                },
                inspectEndDayOfMonth: {
                    maxTo: "输入的值必须大于前者"
                }
            }
        });

        //设置各代分红
        this.setInterestReward();
    },
    setInterestReward: function () {
        var rewardSerialization = "";
        for (var i = 1; i <= interestCount; i++) {
            var interestRate = $('input[name=interestRate' + i + ']').val();
            var interestReach = $('input[name=interestReach' + i + ']').val();
            var interestMax = $('input[name=interestMax' + i + ']').val();
            if (i == interestCount) {
                rewardSerialization += interestRate + "," + interestReach + "," + interestMax
            } else {
                rewardSerialization += interestRate + "," + interestReach + "," + interestMax + "|";
            }
        }
        $("input[name=rewardSerialization]").val(rewardSerialization);
    }
};
