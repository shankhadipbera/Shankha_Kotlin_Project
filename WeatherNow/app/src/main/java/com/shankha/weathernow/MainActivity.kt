package com.shankha.weathernow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.google.gson.Gson
import com.shankha.weathernow.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

//https://api.openweathermap.org/data/2.5/
//1074a2b3d9981035e6d35044a105839e

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        fetchWeatherdata("Kolkata")
        val stringArray = arrayOf("New York", "London", "Paris", "Tokyo", "Beijing", "Sydney", "Toronto", "Berlin", "Moscow", "Rio de Janeiro", "Cairo", "Seoul", "Rome", "Amsterdam", "Istanbul", "Buenos Aires", "Dubai", "Bangkok", "Athens", "Stockholm",
            "Vienna", "Prague", "Helsinki", "Copenhagen", "Oslo", "Auckland", "Santiago", "Lima", "Bogota", "Nairobi", "Johannesburg", "Madrid", "Barcelona", "Budapest", "Warsaw", "Bucharest", "Kiev", "Zagreb", "Lisbon", "Brussels", "Vienna", "Munich",
            "Frankfurt", "Zurich", "Geneva", "Sydney", "Melbourne", "Brisbane", "Perth", "Adelaide", "Auckland", "Wellington", "Christchurch", "Montreal", "Vancouver", "Calgary", "Edmonton", "Ottawa", "Quebec City", "Winnipeg", "Halifax", "St. John's",
            "San Francisco", "Los Angeles", "Chicago", "Houston", "Miami", "Boston", "Philadelphia", "Atlanta", "Dallas", "Seattle", "Denver", "Phoenix", "Las Vegas", "Honolulu", "San Diego", "Mexico City", "Guadalajara", "Monterrey", "Tijuana", "Cancun",
            "Sao Paulo", "Rio de Janeiro", "Buenos Aires", "Santiago", "Lima", "Bogota", "Quito", "Caracas", "Montevideo", "Asuncion", "La Paz", "Santa Cruz", "Managua", "San Jose", "Panama City", "San Salvador", "Guatemala City", "Tegucigalpa",
            "San Pedro Sula", "San Juan", "Santo Domingo", "Havana", "Kingston", "Port-au-Prince", "Nassau", "Bridgetown", "Georgetown", "Paramaribo", "Montego Bay", "Port of Spain", "Bridgetown", "Castries", "Port Vila", "Suva", "Nuku'alofa",
            "Apia", "Honiara", "Majuro", "Palikir", "Koror", "Port Moresby", "Port Vila", "Noumea", "Papeete", "Hanoi", "Ho Chi Minh City", "Phnom Penh", "Bangkok", "Manila", "Jakarta", "Kuala Lumpur", "Singapore", "Yangon", "Jakarta",
            "Manila", "Kuala Lumpur", "Singapore", "Yangon", "Phnom Penh", "Bangkok", "Hanoi", "Ho Chi Minh City", "Taipei", "Hong Kong", "Beijing", "Shanghai", "Guangzhou", "Shenzhen", "Seoul", "Busan", "Osaka", "Nagoya", "Fukuoka", "Sapporo",
            "Taipei", "Kaohsiung", "Taichung", "Tainan", "Sendai", "Hiroshima", "Kobe", "Kyoto", "Osaka", "Nagoya", "Fukuoka", "Sapporo", "Yokohama", "Kawasaki", "Saitama", "Chiba", "Okinawa", "Osaka", "Kobe", "Kyoto", "Nagoya", "Fukuoka", "Sapporo",
            "Sendai", "Hiroshima", "Yokohama", "Kawasaki", "Saitama", "Chiba", "Okinawa", "Osaka", "Kobe", "Kyoto", "Nagoya", "Fukuoka", "Sapporo", "Yokohama", "Kawasaki", "Saitama", "Chiba", "Okinawa", "Manila", "Quezon City", "Caloocan", "Makati",
            "Pasig", "Taguig", "Mandaluyong", "Manila", "Quezon City", "Caloocan", "Makati", "Pasig", "Taguig", "Mandaluyong", "Jakarta", "Surabaya", "Bandung", "Medan", "Semarang", "Palembang", "Tangerang", "South Tangerang", "Makassar", "Bogor",
            "Batam", "Depok", "Balikpapan", "Manado", "Pontianak", "Padang", "Pekanbaru", "Bandar Lampung", "Samarinda", "Banjarmasin", "Surakarta", "Denpasar", "Malang", "Tasikmalaya", "Mataram", "Jambi", "Cirebon", "Purwokerto", "Probolinggo",
            "Salatiga", "Tegal", "Cilegon", "Cilacap", "Bengkulu", "Pematangsiantar", "Kupang", "Pasuruan", "Sukabumi", "Ponorogo", "Mojokerto", "Magelang", "Sorong", "Palu", "Manokwari", "Ambon", "Jayapura", "Merauke", "Serang", "Banjarbaru",
            "Tanjung Pinang", "Gorontalo", "Dumai", "Bitung", "Lhokseumawe", "Tebing Tinggi", "Metro", "Sungai Penuh", "Bukittinggi", "Palopo", "Bontang", "Padang Sidempuan", "Ternate", "Tomohon", "Singkawang", "Cianjur", "Singaraja", "Ende",
            "Maumere", "Sampit", "Tarakan", "Palangkaraya", "Pangkal Pinang", "Banda Aceh", "Langsa", "Sabang", "Tanjung Balai", "Tanjung Pinang", "Bukittinggi", "Padang Sidempuan", "Pekanbaru", "Payakumbuh", "Padangpanjang", "Bukit Tinggi", "Solok",
            "Sawahlunto", "Lubuklinggau", "Sungailiat", "Batam", "Tanjung Pinang", "Papendrecht", "Ijsselstein", "Katwijk", "Leiderdorp", "Krimpen aan den IJssel", "Mumbai", "Delhi", "Bangalore", "Hyderabad", "Chennai", "Kolkata", "Pune", "Ahmedabad",
            "Jaipur", "Surat", "Lucknow", "Kanpur", "Nagpur", "Indore", "Thane", "Bhopal", "Visakhapatnam", "Pimpri-Chinchwad", "Patna", "Vadodara", "Ghaziabad", "Ludhiana", "Agra", "Nashik", "Faridabad", "Meerut", "Rajkot", "Kalyan-Dombivali",
            "Vasai-Virar", "Varanasi", "Srinagar", "Aurangabad", "Dhanbad", "Amritsar", "Navi Mumbai", "Allahabad", "Ranchi", "Howrah", "Coimbatore", "Jabalpur", "Gwalior", "Vijayawada", "Jodhpur", "Madurai", "Raipur", "Kota", "Chandigarh", "Guwahati",
            "Solapur", "Hubli-Dharwad", "Tiruchirappalli", "Bareilly", "Moradabad", "Mysore", "Tiruppur", "Gurgaon", "Aligarh", "Jalandhar", "Bhubaneswar", "Salem", "Warangal", "Guntur", "Bhiwandi", "Saharanpur", "Gorakhpur", "Bikaner", "Amravati", "Noida",
            "Jamshedpur", "Bhilai", "Cuttack", "Firozabad", "Kochi", "Nellore", "Bhavnagar", "Dehradun", "Durgapur", "Asansol", "Rourkela", "Nanded", "Kolhapur", "Ajmer", "Akola", "Gulbarga", "Jamnagar", "Ujjain", "Loni", "Siliguri", "Jhansi", "Ulhasnagar",
            "Jammu", "Sangli-Miraj & Kupwad", "Mangalore", "Erode", "Belgaum", "Ambattur", "Tirunelveli", "Malegaon", "Gaya", "Jalgaon", "Udaipur", "Maheshtala", "Tirupati", "Davanagere", "Kozhikode", "Akbarpur", "Shahjahanpur", "Rajpur Sonarpur",
            "Bokaro Steel City", "South Dumdum", "Rajahmundry", "Kharagpur", "Bilaspur", "Mathura", "Kamarhati", "Avadi", "Rampur", "Shivamogga", "Chandrapur", "Junagadh", "Thoothukudi", "Kakinada", "Mahbubnagar", "Bally", "Patiala", "Secunderabad",
            "Nizamabad", "Kollam", "Kadapa", "Vizianagaram", "Baranagar", "Hazaribagh", "Bhatpara", "Agartala", "Muzaffarnagar", "Bhilwara", "Muzaffarpur", "Panihati", "Latur", "Dhule", "Tiruvottiyur", "Kapurthala", "Haldia", "Raiganj", "Dewas", "Gandhinagar",
            "Naihati", "Guntakal", "Barasat", "Darbhanga", "Sambalpur", "Silchar", "Purnia", "Surendranagar", "Chinsurah", "Hajipur", "Shimla", "Kumbakonam", "Bettiah", "Hindupur", "Raniganj", "Haldwani-cum-Kathgodam", "Vidisha", "Purulia", "Rae Bareli", "Vejalpur",
            "Robertsonpet", "Tenali", "Ganganagar", "Burhanpur", "Kharar", "Nandyal", "Morena", "Masaurhi", "Ongole", "Madhyamgram", "Habra", "Deoghar", "Sasaram", "Hazaribagh", "Munger", "Panchkula", "Suryapet", "Sambhal", "Singrauli", "Hoshiarpur", "Narasaraopet",
            "Chittoor", "Dibrugarh", "Guntur", "Unnao", "Kollam", "Tirupati", "Gadag-Betigeri", "Thane", "Kollam", "Tirupati", "Gadag-Betigeri", "Ganganagar", "Burhanpur", "Kharar", "Nandyal", "Morena", "Masaurhi", "Ongole", "Madhyamgram", "Habra", "Deoghar", "Sasaram",
            "Hazaribagh", "Munger", "Panchkula", "Suryapet", "Sambhal", "Singrauli", "Hoshiarpur", "Narasaraopet", "Chittoor", "Dibrugarh", "Guntur", "Unnao", "Kollam", "Tirupati", "Gadag-Betigeri", "Thane", "Kollam", "Tirupati", "Gadag-Betigeri", "Shivpuri", "Ambarnath",
            "Puducherry", "Kishanganj", "Gondia", "Adoni", "Gangapur", "Madanapalle", "Kalyani", "Malegaon", "Gadwal", "Gudur", "Tandur", "Jatani", "Jaigaon", "Lanka", "Neyyattinkara", "Amod", "Mangrol", "Anandnagar", "Pachore", "Pukhrayan", "Manendragarh", "Wara Seoni",
            "Rehli", "Sabalgarh", "Tulsipur", "Kareli", "Maharajpur", "Mehidpur", "Rehti", "Vijaypur", "Tirwaganj", "Mau Aimma", "Sarsod", "Rudauli", "Sadabad", "Pihani", "Purwa", "Nawabganj", "Naraini", "Sikanderpur", "Raisen", "Lahar", "Mauganj", "Sabalgarh", "Shujalpur",
            "Seoni-Malwa", "Nepanagar", "Sonkatch", "Shahpur", "Sheopur", "Raghogarh-Vijaypur", "Rahatgarh", "Lahar", "Mauganj", "Sabalgarh", "Shujalpur", "Seoni-Malwa", "Nepanagar", "Sonkatch", "Shahpur", "Sheopur", "Raghogarh-Vijaypur", "Rahatgarh", "Khairagarh", "Sakti",
            "Dalli-Rajhara", "Bemetara", "Bade Bacheli", "Mungeli", "Pasighat", "Bhawanipatna", "Bargarh", "Jatani", "Kendrapara", "Rayagada", "Champa", "Dalli-Rajhara", "Bemetara", "Bade Bacheli", "Mungeli", "Pasighat", "Bhawanipatna", "Bargarh", "Jatani", "Kendrapara",
            "Rayagada", "Champa", "Gidam", "Marhaura", "Naharlagun", "Nirjuli", "Kovvur", "Gauripur", "Goalpara", "Bokakhat", "Namrup", "Kailasahar", "Khowai", "Udaipur", "Dharmanagar", "Ranirbazar", "Sonamura", "Kulgam", "Anantnag", "Yol", "Nagrota", "Kathua", "Samba",
            "Bashohli", "Banihal", "Padam", "Kud", "Akhnoor", "Rajauri", "Poonch", "Kishtwar", "Bhaderwah", "Ramban", "Reasi", "Batote", "Kud", "Akhnoor", "Rajauri", "Poonch", "Kishtwar", "Bhaderwah", "Ramban", "Reasi", "Batote", "Kud", "Akhnoor", "Rajauri", "Poonch",
            "Kishtwar", "Bhaderwah", "Ramban", "Reasi", "Batote", "Kud", "Akhnoor", "Rajauri", "Poonch", "Kishtwar", "Bhaderwah", "Ramban", "Reasi", "Gorakhpur", "Khurja", "Sikandrabad", "Banda", "Buxar", "Hardoi", "Auraiya", "Basti", "Mathura", "Hathras", "Jaunpur",
            "Shikohabad", "Ghazipur", "Mughalsarai", "Amroha", "Maunath Bhanjan", "Hapur", "Lakhimpur", "Banda", "Buxar", "Hardoi", "Auraiya", "Basti", "Mathura", "Hathras", "Jaunpur", "Shikohabad", "Ghazipur", "Mughalsarai", "Amroha", "Maunath Bhanjan", "Hapur",
            "Lakhimpur", "Tadepallegudem", "Rajahmundry", "Narasapur", "Palasa Kasibugga", "Parvathipuram", "Srikakulam", "Narsipatnam", "Gunupur", "Tuni", "Bheemunipatnam", "Venkatagiri", "Yellandu", "Sirpur", "Tiruvuru", "Thiruthuraipoondi", "Shenkottai", "Vikarabad",
            "Jeypore", "Kollapur", "Shamli", "Darbhanga", "Ranibennur", "Kovvur", "Kundapura", "Obra", "Madhubani", "Sattenapalle", "Kalyandurg", "Uravakonda", "Sadasivpet", "Tekkali", "Samalkota", "Kuchinda", "Chitrakoot", "Balangir", "Nabarangapur", "Balangir",
            "Nabarangapur", "Kuchinda", "Balangir", "Nabarangapur", "Kuchinda", "Balangir", "Nabarangapur", "Jevargi", "Navalgund", "Sidlaghatta", "Mudalagi", "Sanduru", "Sagara", "Lingsugur", "Vijayapura", "Malur", "Sankeshwar", "Vijapur", "Lal Gopalganj Nindaura",
            "Mathabhanga", "Madarihat", "Makhdumpur",  "Raghunathganj", "Medinipur")

        val adpt=ArrayAdapter(this,android.R.layout.simple_list_item_1,stringArray)
        val serchL=findViewById<AutoCompleteTextView>(R.id.edtext)
        serchL.threshold=2
        serchL.setAdapter(adpt)
        serchL.setOnItemClickListener { parent, view, position, id ->
            hideKeyboard()
        }
        searchLocation()
    }


    private fun searchLocation() {
        binding.searchbutton.setOnClickListener {
            val location=binding.edtext.text.toString()
            fetchWeatherdata(location)
        }
    }

    private fun fetchWeatherdata(city:String) {
       val retrofit=Retrofit.Builder()
           .addConverterFactory(GsonConverterFactory.create())
           .baseUrl("https://api.openweathermap.org/data/2.5/")
           .build().create(ApiInterface::class.java)

        val response=retrofit.getWeatherData(city,"1074a2b3d9981035e6d35044a105839e","metrics")
        response.enqueue(object : Callback<WeatherApp>{
            override fun onResponse(call: Call<WeatherApp>, response: Response<WeatherApp>) {
                val responseBody=response.body()
                if (response.isSuccessful && responseBody!=null){
                    val temperature= responseBody?.main?.temp
                    var pressure=responseBody.main.pressure
                    val max_temperature=responseBody.main.temp_max
                    val min_temperature=responseBody.main.temp_min
                    val humudity=responseBody.main.humidity
                    val feelsLike=responseBody.main.feels_like
                    val sunRise=responseBody.sys.sunrise.toLong()
                    val sun_set=responseBody.sys.sunset.toLong()
                    val wind_Speed=responseBody.wind.speed
                    val  condition=responseBody.weather.firstOrNull()?.main?:"unknown"



                    binding.temperature.text="%.2f °C".format((temperature?.minus(273))!!.toFloat())
                    binding.maxtemp.text="%.2f °C".format((max_temperature?.minus(273))!!.toFloat())
                    binding.mintemp.text="%.2f °C".format((min_temperature?.minus(273))!!.toFloat())
                    binding.wind.text="$wind_Speed m/s"
                    binding.pressure.text="$pressure hPa"
                    binding.humidity.text="$humudity %"
                    binding.sunrise.text="${timeS(sunRise)}"
                    binding.sunset.text="${timeS(sun_set)}"
                    binding.weather.text="$condition"
                    binding.weather2.text="$condition"
                    binding.Location.text="$city"
                    binding.day.text= dayName(System.currentTimeMillis())
                    binding.date.text=dateName()

                    changeItemAcordingToCondition(condition)

}
}

override fun onFailure(call: Call<WeatherApp>, t: Throwable) {
TODO("Not yet implemented")
}

})


}

    private fun changeItemAcordingToCondition(conditionS:String) {
        when(conditionS){

            "Clear Sky"->{
                binding.layout.setBackgroundResource(R.drawable.clearsky)
                binding.animation.setAnimation(R.raw.clearskyani)
                binding.condition.setImageResource(R.drawable.clearskysvg)
            }
            "Sunny"->{
                binding.layout.setBackgroundResource(R.drawable.sunny)
                binding.animation.setAnimation(R.raw.sunnyani)
                binding.condition.setImageResource(R.drawable.sunnysvg)
            }
            "Partly Clouds"->{
                binding.layout.setBackgroundResource(R.drawable.partlycloud)
                binding.animation.setAnimation(R.raw.partlycloudani)
                binding.condition.setImageResource(R.drawable.partlycloudsvg)
            }
            "Clouds"->{
                binding.layout.setBackgroundResource(R.drawable.cloud)
                binding.animation.setAnimation(R.raw.cloud_ani)
                binding.condition.setImageResource(R.drawable.cloudssvg)
            }
            "Overcast"->{
                binding.layout.setBackgroundResource(R.drawable.overcast)
                binding.animation.setAnimation(R.raw.overcastani)
                binding.condition.setImageResource(R.drawable.overcastp)
            }
            "Mist"->{
                binding.layout.setBackgroundResource(R.drawable.mist)
                binding.animation.setAnimation(R.raw.mistcloudani)
                binding.condition.setImageResource(R.drawable.mistp)
            }
            "Foggy"->{
                binding.layout.setBackgroundResource(R.drawable.foggy)
                binding.animation.setAnimation(R.raw.foggyani)
                binding.condition.setImageResource(R.drawable.foggyp)
            }
            "Light Rain"->{
                binding.layout.setBackgroundResource(R.drawable.light_rain)
                binding.animation.setAnimation(R.raw.lightrainani)
                binding.condition.setImageResource(R.drawable.lightrainsvg)
            }
            "Moderate Rain"->{
                binding.layout.setBackgroundResource(R.drawable.moderate_rain)
                binding.animation.setAnimation(R.raw.lightrainani)
                binding.condition.setImageResource(R.drawable.moderaterainsvg)
            }
            "Heavy Rain"->{
                binding.layout.setBackgroundResource(R.drawable.heavy_rain)
                binding.animation.setAnimation(R.raw.heavyrainani)
                binding.condition.setImageResource(R.drawable.heavyrainsvg)
            }
            "Drizzle"->{
                binding.layout.setBackgroundResource(R.drawable.drizzle)
                binding.animation.setAnimation(R.raw.drizzleani)
                binding.condition.setImageResource(R.drawable.drizzlesvg)
            }
            "Showers"->{
                binding.layout.setBackgroundResource(R.drawable.moderate_rain)
                binding.animation.setAnimation(R.raw.showerani)
                binding.condition.setImageResource(R.drawable.showersvg)
            }
            "Light snow"->{
                binding.layout.setBackgroundResource(R.drawable.lightsnow)
                binding.animation.setAnimation(R.raw.lightsnowani)
                binding.condition.setImageResource(R.drawable.lightsnowj)
            }
            "Moderate Snow"->{
                binding.layout.setBackgroundResource(R.drawable.moderatesnow)
                binding.animation.setAnimation(R.raw.moderatesnowani)
                binding.condition.setImageResource(R.drawable.moderatesnowsvg)
            }
            "Heavy Snow"->{
                binding.layout.setBackgroundResource(R.drawable.heavy_snow)
                binding.animation.setAnimation(R.raw.heavysnowani)
                binding.condition.setImageResource(R.drawable.heavysnowsvg)
            }
            "Blizzard"->{
                binding.layout.setBackgroundResource(R.drawable.blizzard)
                binding.animation.setAnimation(R.raw.blizzerdani)
                binding.condition.setImageResource(R.drawable.blizzardp)
            }
            "Sunny"->{
                binding.layout.setBackgroundResource(R.drawable.sunny)
                binding.animation.setAnimation(R.raw.sunnyani)
                binding.condition.setImageResource(R.drawable.sunnysvg)
            }
            else ->{
                binding.layout.setBackgroundResource(R.drawable.clearsky)
                binding.animation.setAnimation(R.raw.clearskyani)
                binding.condition.setImageResource(R.drawable.clearskysvg)
            }

        }
        binding.animation.playAnimation()

                          }

    fun dayName(timestamp: Long):String{
        val simpleDateFormat=SimpleDateFormat("EEEE", Locale.getDefault())
        return simpleDateFormat.format((Date()))
    }

    fun timeS(timestamp: Long):String{
        val simpleDateFormat=SimpleDateFormat("HH:mm", Locale.getDefault())
        return simpleDateFormat.format((Date(timestamp*1000)))
    }

    fun dateName():String{
        val simpleDateFormat=SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        return simpleDateFormat.format((Date()))
    }
    private fun hideKeyboard() {
        val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        val currentFocusView = currentFocus
        currentFocusView?.let {
            imm.hideSoftInputFromWindow(it.windowToken, 0)
            it.clearFocus()
        }
    }
}