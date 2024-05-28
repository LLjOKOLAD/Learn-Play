package com.example.learnplay.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.learnplay.ApiClient
import com.example.learnplay.DbHelper
import com.example.learnplay.activities.Notification
import com.example.learnplay.viewModels.LearningViewModel
import com.example.learnplay.R
import com.example.learnplay.activities.TasksChallenging
import com.example.learnplay.adapters.SectionAdapter
import com.example.learnplay.adapters.SectionItem
import com.example.learnplay.dataClasses.TopicNetwork
import com.example.learnplay.dataClasses.TopicRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LearningFg : Fragment() {

    companion object {
        fun newInstance() = LearningFg()
    }

    private lateinit var viewModel: LearningViewModel
    private val sectionItems = mutableListOf<SectionItem>()
    lateinit var recyclerView: RecyclerView
    private lateinit var adapter: SectionAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? { val view = inflater.inflate(R.layout.fragment_learning_fg, container, false)


        recyclerView = view.findViewById(R.id.recyclerViewLearning)
        val layoutManager = LinearLayoutManager(requireContext())
        recyclerView.layoutManager = layoutManager

        val notButt:ImageView = view.findViewById(R.id.notif_but)

        notButt.setOnClickListener(){
            startActivity(Intent(requireContext(), Notification::class.java))
        }

        val sectionItems = mutableListOf(
            SectionItem("Раздел 1", "Клартиры и садовые участки", R.drawable.map_reg_button,5),
            SectionItem("Раздел 2", "Автомобили", R.drawable.map_reg_dess_button,5),
            SectionItem("Раздел 3", "Компьютеры", R.drawable.map_reg_dess_button,1),
            SectionItem("Раздел 4", "Телефоны", R.drawable.map_reg_button,0)
        )


        val db = DbHelper(requireContext(),null)
        val user = db.getLogUser()
        if (user != null) {
            authenticateUser(user.email,user.pass)
        }
        db.close()



        return view
    }

    private fun authenticateUser(username: String, password: String) {
        val call = ApiClient.apiService.login(username, password)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    fetchUserData()
                } else {
                    Toast.makeText(requireContext(), "Ошибка аутентификации: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(requireContext(), "Ошибка: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun fetchUserData() {
        val call = ApiClient.apiService.getTopics()
        call.enqueue(object : Callback<List<TopicNetwork>> {
            override fun onResponse(call: Call<List<TopicNetwork>>, response: Response<List<TopicNetwork>>) {
                if (response.isSuccessful) {
                    val responseData = response.body()
                    responseData?.let {
                        //////////////////////
                        populateSectionItems(it)
                    }
                } else {
                    Toast.makeText(requireContext(), "Ошибка получения данных: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<TopicNetwork>>, t: Throwable) {
                Toast.makeText(requireContext(), "Ошибка: ${t.message}", Toast.LENGTH_SHORT).show()
                Log.d("Resp","Ошибка: ${t.message}")
            }
        })
    }


    private fun populateSectionItems(topics: List<TopicNetwork>) {
        sectionItems.clear()
        for (topic in topics) {
            val imageResource = getImageResourceForTopic(topic.topicName)
            sectionItems.add(SectionItem("Раздел ${topic.topicId}", topic.topicName, imageResource, topic.step))
        }
        Log.d("Section",sectionItems.toString())

        adapter = SectionAdapter(sectionItems, object : SectionAdapter.OnButtonClickListener {
            override fun onButtonClick(sectionPosition: Int, buttonPosition: String) {
                // Обработка нажатия на кнопку
                if(buttonPosition == "What"){
                    val pos = sectionPosition + 1
                    when(pos){
                        1 ->{
                            Toast.makeText(requireContext(),"Справочник № $pos",Toast.LENGTH_LONG).show()
                        }
                        2 ->{
                            Toast.makeText(requireContext(),"Справочник № $pos",Toast.LENGTH_LONG).show()
                        }
                        3 ->{
                            Toast.makeText(requireContext(),"Справочник № $pos",Toast.LENGTH_LONG).show()
                        }
                        4 ->{
                            Toast.makeText(requireContext(),"Справочник № $pos",Toast.LENGTH_LONG).show()
                        }
                        5 ->{
                            Toast.makeText(requireContext(),"Справочник № $pos",Toast.LENGTH_LONG).show()
                        }
                        6 ->{
                            Toast.makeText(requireContext(),"Справочник № $pos",Toast.LENGTH_LONG).show()
                        }
                        7 ->{
                            Toast.makeText(requireContext(),"Справочник № $pos",Toast.LENGTH_LONG).show()
                        }
                        8 ->{
                            Toast.makeText(requireContext(),"Справочник № $pos",Toast.LENGTH_LONG).show()
                        }
                        else ->{
                            Toast.makeText(requireContext(),"Справочник № $pos",Toast.LENGTH_LONG).show()
                        }

                    }

                }
                else{
                    getTopicQuest(sectionPosition,buttonPosition.toInt())
                }

            }
        })
        recyclerView.adapter = adapter
    }



    private fun getImageResourceForTopic(topicName: String): Int {
        // Замените это соответствующими ресурсами изображений для ваших разделов
        return when (topicName) {
            "Вероятность и статистика" -> R.drawable.map_reg_button
            // Добавьте сюда другие условия для других разделов
            else -> R.drawable.map_reg_button // Замените на ваше изображение по умолчанию
        }
    }



    private fun getTopicQuest(topicId:Int, step:Int){
        val request = TopicRequest(topicId + 1, step)
        val call = ApiClient.apiService.startTopicQuest(request)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    startQuesting()
                } else {
                    Toast.makeText(requireContext(), "Error: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(requireContext(), "Request failed: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun startQuesting(){
        val intent = Intent(requireContext(),TasksChallenging::class.java)
        startActivity(intent)
    }





}

