<template>
  <div>
    <div v-if="bossReview === ''" style="height: 320px; text-align: center; font-size: 23px; opacity: 0.5" class="pa-12">
      아직 후기가 없어요.
    </div>

    <div v-else>
      <div v-for="(boss, index) in bossReview" :key="index" style="padding: 20px 15px 0 20px; border-bottom: 1px solid rgba(187,187,187,0.23);">
        <div style="display: flex; margin-top: 2px">
          <div style="padding-right: 10px"><v-img :src="require(`@/assets/profile/${boss.profileImg}`)" max-height="60" max-width="60"></v-img></div>
          <div>
            <div style="font-size: 22px; font-weight: bolder">{{boss.name}}</div>
            <div> {{boss.region}} ㆍ {{ boss.date | timeForToday }}</div>
          </div>
        </div>

        <div style="padding-top: 20px; font-size: 25px">
          {{boss.content}}
        </div>

        <div v-if="bossReviewImg[index][0]">
          <swiper class="swiper" :options="swiperOption" style="height: 500px">
            <swiper-slide v-for="(img) in bossReviewImg[index]" :key="img.no">
              <v-img max-height="550px" :src="require(`@/assets/bossReview/${img.imgName}`)" id="img"></v-img>
            </swiper-slide>

            <div class="swiper-pagination" slot="pagination"></div>
          </swiper>
        </div>
        <div v-else></div>

        <div style="padding-bottom: 8px">
          <v-chip-group multiple v-model="selection[index]" active-class="light-green lighten-2 white--text">
            <v-chip v-for="(tag, index) in reviewTag" :key="index">
              {{ tag }}
            </v-chip>
          </v-chip-group>
        </div>
      </div>

    </div>
  </div>
</template>

<script>
import {mapActions, mapState} from "vuex";
import 'swiper/css/swiper.css'
import {Swiper, SwiperSlide} from "vue-awesome-swiper";

export default {
  name: "BossReviewListView",
  props: {
    bossNo: {
      type: Number,
    }
  },
  components: {
    Swiper, SwiperSlide
  },
  data(){
    return {
      selection: [],
      reviewTag: [
        '친절해요',
        '가격이 저렴해요',
        '만족스러워요',
        '아쉬워요'
      ],
      swiperOption: {
        slidesPerView: 1,
        spaceBetween: 30,
        autoHeight: true,
        loop: false,
        pagination: {
          el: '.swiper-pagination',
          clickable: true
        }
      },
    }
  },
  methods: {
    ...mapActions(['fetchBossReview']),
    ...mapActions(['fetchBossReviewImage']),
    parsingState() {
      for(let i = 0; i < this.bossReview.length; i++) {
        let num = this.bossReview[i].state.split(',');
        let arr = [];

        for(let j = 0; j < num.length; j++){
          arr.push(parseInt(num[j]));
        }

        console.log(arr)
        this.selection.push(arr);
      }
    }
  },
  computed: {
    ...mapState(['bossReview']),
    ...mapState(['bossReviewImg'])
  },
  async mounted() {
    let num = '0' +this.bossNo;
    await this.fetchBossReview(num);
    setTimeout(() => {
      this.fetchBossReviewImage(num)
    }, 80)
    await this.parsingState();

    //alert(this.bossReview)
  }
}
</script>

<style scoped>

</style>