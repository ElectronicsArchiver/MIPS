package org.parker.mips.GUI.hexeditor;

class accent {

   public static final String[] s = new String[]{"@AaÀÁÂÃÄÅàáâãäåĀāĂăĄąǍǎǞǟǠǡǺǻȀȁȂȃȦȧȺḀḁẚẠạẢảẤấẦầẨẩẪẫẬậẮắẰằẲẳẴẵẶặ₳ÅⒶⓐⱥⱯ＠Ａａ", "BbƀƁƂƃƄƅɃɓḂḃḄḅḆḇℬ␢ⒷⓑＢｂ", "Cc¢©ÇçĆćĈĉĊċČčƇƈȻȼḈḉ₡₢ℂ℃℄ℭⒸⓒꜾꜿＣｃ", "DdÐðĎďĐđƉƊƋƌȡɖɗḊḋḌḍḎḏḐḑḒḓ₫₯ⅅⅆ∂ⒹⓓꝩꝺＤｄ", "EeÈÉÊËèéêëĒēĔĕĖėĘęĚěƎǝȄȅȆȇȨȩḔḕḖḗḘḙḚḛḜḝẸẹẺẻẼẽẾếỀềỂểỄễỆệ₠€ℇ℮ℯℰⅇⒺⓔⱸⱻＥｅ", "FfƑƒḞḟ₣℉ℱℲ⅍ⒻⓕꜰꝫꝼꟻＦｆ", "GgĜĝĞğĠġĢģƓǤǥǦǧǴǵɠɡḠḡ₲ℊ⅁ⒼⓖꝽꝾꝿＧｇ", "HhĤĥĦħȞȟḢḣḤḥḦḧḨḩḪḫẖ₴ℋℌℍℎℏⒽⓗⱧⱨⱵⱶＨｈ", "IiÌÍÎÏìíîïĨĩĪīĬĭĮįİıƗǏǐȈȉȊȋɨӀḬḭḮḯỈỉỊịℐℑℹⅈⒾⓘꟾＩｉ", "JjĴĵǰȷɈɉⅉⒿⓙⱼＪｊ", "KkĶķĸƘƙǨǩḰḱḲḳḴḵ₭KⓀⓚⱩⱪꝀꝁꝂꝃꝄꝅＫｋ", "Ll£ĹĺĻļĽľĿŀŁłƚȴȽḶḷḸḹḺḻḼḽ₤₶ℒℓ℔⅂⅃ⓁⓛⱠⱡⱢꝆꝇꝈꝉꞀꞁＬｌ", "MmƜɯḾḿṀṁṂṃ₥ℳⓂⓜⱮꟽꟿＭｍ", "NnÑñŃńŅņŇňŉŊŋƝƞǸǹȠȵɲṄṅṆṇṈṉṊṋ₦₪ℕ№ⓃⓝＮｎ", "OoÒÓÔÕÖØòóôõöøŌōŎŏŐőƆƟƠơǑǒǪǫǬǭǾǿȌȍȎȏȪȫȬȭȮȯȰȱɔɵṌṍṎṏṐṑṒṓỌọỎỏỐốỒồỔổỖỗỘộỚớỜờỞởỠỡỢợℴ∅ⓄⓞⱺꝊꝋꝌꝍＯｏ", "PpƤƥṔṕṖṗ₧₱℗℘ℙⓅⓟⱣꝐꝑꝒꝓꝔꝕꟼＰｐ", "QqɊɋℚ℺ⓆⓠꝖꝗꝘꝙＱｑ", "Rr®ŔŕŖŗŘřȐȑȒȓɌɍṘṙṚṛṜṝṞṟ₨ℛℜℝ℞℟ⓇⓡⱤⱹꝚꝛꞂꞃＲｒ", "$SsŚśŜŝŞşŠšſȘșȿṠṡṢṣṤṥṦṧṨṩẛẜẝ₷⅀ⓈⓢⱾꜱꞄꞅ＄Ｓｓ", "TtŢţŤťŦŧƫƬƭƮȚțȶȾʈṪṫṬṭṮṯṰṱẗ₮₸ⓉⓣⱦꞆꞇＴｔ", "UuÙÚÛÜùúûüŨũŪūŬŭŮůŰűŲųƯưǓǔǕǖǗǘǙǚǛǜȔȕȖȗɄʉṲṳṴṵṶṷṸṹṺṻỤụỦủỨứỪừỬửỮữỰựⓊⓤＵｕ", "VvƲɅʋʌṼṽṾṿỼỽⓋⓥⱱⱴⱽꝞꝟＶｖ", "WwŴŵẀẁẂẃẄẅẆẇẈẉẘ₩ⓌⓦⱲⱳＷｗ", "Xx×ẊẋẌẍⓍⓧＸｘ", "Yy¥ÝýÿŶŷŸƳƴȲȳɎɏẎẏẙỲỳỴỵỶỷỸỹỾỿ⅄ⓎⓨＹｙ", "ZzŹźŻżŽžƵƶȤȥɀẐẑẒẓẔẕℤℨⓏⓩⱫⱬⱿꝢꝣＺｚ", "ÞþꝤꝥꝦꝧ", "ÆæǢǣǼǽ", "Œœɶ", "0⁰⓪⓿０", "1¹ⁱⅠⅰ①⑰⒈⓵１", "2²⁲Ⅱⅱ②⑵⒉⓶２", "3³⁳Ⅲⅲ③⑶⒊⓷３", "4⁴Ⅳⅳ④⑷⒋⓸４", "5⁵Ⅴⅴ⑤⑸⒌⓹５", "6⁶Ⅵⅵ⑥⑹⒍⓺６", "7⁷Ⅶⅶ⑦⑺⒎⓻７", "8Ȣȣ⁸Ⅷⅷ⑧⑻⒏⓼８", "9⁹Ⅸⅸ⑨⑼⒐⓽９", "ƷƸƹǮǯȜȝʒ", "ꜸꜹꜺꜻ", "ƿǷᚹ", "ǄǅǆǱǲǳ", "Ǉǈǉ", "Ǌǋǌ", "℡☎☏✆", "ɑΆΑάα∝ⱭⱰ", "ßΒβϐẞ", "ƔɣΓγℽℾ", "ƍΔδẟ∆", "Εεϵ϶∍ƐɛΈέ", "ΗηΉή", "Θθϑ", "ƖɩͅͺΊΙΪίιϊι℩", "Κκϰ", "ƛΛλ", "µΜμ", "ΌΟοό", "Ππϖℼℿ∏", "Ρρϱϼ", "ƩʃͻͼͽΣςσϚϛϲϹϽϾϿ∑", "ƱʊΎΥΫΰυϋύϒϔ℧☋", "ɸΦφϕⱷ", "ΏΩωώΩ☊", "ϘϙϞϟ", "ͲͳϠϡ", "Ͱͱ", "Ͷͷ"};


}
