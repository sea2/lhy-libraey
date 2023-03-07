package com.yunlan.baselibrary.util;


import com.github.sea2.R;
import com.yunlan.baselibrary.bean.EmojiBen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by lhy on 2022/4/9.
 */

public class EmojiUtil {



    static EmojiUtil mEmojiUtil=new EmojiUtil();

    Map<String,Integer> map=null;


   public static EmojiUtil getInstance(){
             return  mEmojiUtil;
    }


    public Map<String, Integer> getMap() {
        if(map==null){
            map=new HashMap<>();
            for(EmojiBen mEmojiBen: getList()){
                if(StringUtils.isNotEmpty(mEmojiBen.getKey())) {
                    map.put(mEmojiBen.getKey().trim(), mEmojiBen.getImgId());
                }
            }
            for(EmojiBen mEmojiBen: getXYList()){
                if(StringUtils.isNotEmpty(mEmojiBen.getKey())) {
                map.put(mEmojiBen.getKey().trim(),mEmojiBen.getImgId());
            }
        }}
        return map;
    }


    /** 仙剑一
     * @return
     */
    public  List<EmojiBen> getList(){
        List<EmojiBen> list=new ArrayList<>();
        // 阿奴表情
        list.add(new EmojiBen(R.drawable.emoji_an_bn,"[阿奴_暴怒]"));
        list.add(new EmojiBen(R.drawable.emoji_an_cg,"[阿奴_吃瓜]"));
        list.add(new EmojiBen(R.drawable.emoji_an_wp,"[阿奴_顽皮]"));
        list.add(new EmojiBen(R.drawable.emoji_an_kk,"[阿奴_哭哭]"));
        list.add(new EmojiBen(R.drawable.emoji_an_sq,"[阿奴_生气]"));
        list.add(new EmojiBen(R.drawable.emoji_an_sd,"[阿奴_双刀]"));
        //酒剑仙表情
        list.add(new EmojiBen(R.drawable.emoji_jjx_fx,"[酒剑仙_抚须]"));
        list.add(new EmojiBen(R.drawable.emoji_jjx_ts,"[酒剑仙_摊手]"));
        list.add(new EmojiBen(R.drawable.emoji_jjx_zj,"[酒剑仙_醉酒]"));
        //李大娘表情
        list.add(new EmojiBen(R.drawable.emoji_ldn_ac,"[李大娘_爱财]"));
        list.add(new EmojiBen(R.drawable.emoji_ldn_bn,"[李大娘_暴怒]"));
        list.add(new EmojiBen(R.drawable.emoji_ldn_bs,"[李大娘_鄙视]"));
        //李逍遥
        list.add(new EmojiBen(R.drawable.emoji_lxy_kl,"[李逍遥_可怜]"));
        list.add(new EmojiBen(R.drawable.emoji_lxy_w,"[李逍遥_喂]"));
        list.add(new EmojiBen(R.drawable.emoji_lxy_ok,"[李逍遥_OK]"));
        list.add(new EmojiBen(R.drawable.emoji_lxy_kk,"[李逍遥_哭哭]"));
        list.add(new EmojiBen(R.drawable.emoji_lxy_sk,"[李逍遥_思考]"));
        list.add(new EmojiBen(R.drawable.emoji_lxy_xa,"[李逍遥_喜爱]"));
        //林月如
        list.add(new EmojiBen(R.drawable.emoji_lyr_dz,"[林月如_点赞]"));
        list.add(new EmojiBen(R.drawable.emoji_lyr_ey,"[林月如_恶意]"));
        list.add(new EmojiBen(R.drawable.emoji_lyr_jx,"[林月如_假笑]"));
        list.add(new EmojiBen(R.drawable.emoji_lyr_kk,"[林月如_哭哭]"));
        list.add(new EmojiBen(R.drawable.emoji_lyr_sq,"[林月如_生气]"));
        list.add(new EmojiBen(R.drawable.emoji_lyr_s_q,"[林月如_帅气]"));
        //赵灵儿表情
        list.add(new EmojiBen(R.drawable.emoji_zle_bx,"[赵灵儿_比心]"));
        list.add(new EmojiBen(R.drawable.emoji_zle_ccs,"[赵灵儿_戳手手]"));
        list.add(new EmojiBen(R.drawable.emoji_zle_dk,"[赵灵儿_大哭]"));
        list.add(new EmojiBen(R.drawable.emoji_zle_mm,"[赵灵儿_卖萌]"));
        list.add(new EmojiBen(R.drawable.emoji_zle_sq,"[赵灵儿_生气]"));
        list.add(new EmojiBen(R.drawable.emoji_zle_yw,"[赵灵儿_疑问]"));

        return list;
    }

    /** 仙剑四
     * @return
     */
    public  List<EmojiBen> getXYList(){
        List<EmojiBen> list=new ArrayList<>();
        //韩菱纱表情
        list.add(new EmojiBen(R.drawable.emoji_hls_dz,"[韩菱纱_点赞]"));
        list.add(new EmojiBen(R.drawable.emoji_hls_hx,"[韩菱纱_害羞]"));
        list.add(new EmojiBen(R.drawable.emoji_hls_jy,"[韩菱纱_加油]"));
        list.add(new EmojiBen(R.drawable.emoji_hls_my,"[韩菱纱_媚眼]"));
        list.add(new EmojiBen(R.drawable.emoji_hls_tt,"[韩菱纱_偷听]"));
        //柳梦璃表情
        list.add(new EmojiBen(R.drawable.emoji_lml_dz,"[柳梦璃_吃饭]"));
        list.add(new EmojiBen(R.drawable.emoji_lml_my,"[柳梦璃_媚眼]"));
        list.add(new EmojiBen(R.drawable.emoji_lml_mt,"[柳梦璃_摸头]"));
        list.add(new EmojiBen(R.drawable.emoji_lml_wy,"[柳梦璃_无语]"));
        list.add(new EmojiBen(R.drawable.emoji_lml_fy,"[柳梦璃_眨眼]"));
        //慕容紫英表情
        list.add(new EmojiBen(R.drawable.emoji_mrzy_dy,"[慕容紫英_得意]"));
        list.add(new EmojiBen(R.drawable.emoji_mrzy_tq,"[慕容紫英_叹气]"));
        list.add(new EmojiBen(R.drawable.emoji_mrzy_tx,"[慕容紫英_吐血]"));
        list.add(new EmojiBen(R.drawable.emoji_mrzy_yh,"[慕容紫英_疑惑]"));
        list.add(new EmojiBen(R.drawable.emoji_mrzy_xy,"[慕容紫英_晕眩]"));
        //云天河表情
        list.add(new EmojiBen(R.drawable.emoji_yth_hj,"[云天河_滑稽]"));
        list.add(new EmojiBen(R.drawable.emoji_yth_kq,"[云天河_哭泣]"));
        list.add(new EmojiBen(R.drawable.emoji_yth_qd,"[云天河_期待]"));
        list.add(new EmojiBen(R.drawable.emoji_yth_sq,"[云天河_生气]"));
        list.add(new EmojiBen(R.drawable.emoji_yth_th,"[云天河_吐魂]"));
        list.add(new EmojiBen(R.drawable.emoji_yth_zj,"[云天河_中箭]"));

        return list;
    }






}
