package com.example.carenta

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.room.Room
import com.example.carenta.databinding.FragmentTrajetBinding


class TrajetFragment : Fragment() {

    lateinit var bindingTrajetFragment : FragmentTrajetBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        bindingTrajetFragment = FragmentTrajetBinding.inflate(inflater,container,false)
        val view = bindingTrajetFragment.root
        return view
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_trajet, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var instance = Room.databaseBuilder(requireActivity(), AppDataBase::class.java, "users_db")
            .allowMainThreadQueries().build()
        var trajet = Trajet(null,"Dimanche","16:00","17:00",200)
        instance.getTrajetDao().addTrajet(trajet)
        //val trajet2 = instance.getTrajetDao().getTrajets().get(0)
        val data = instance.getTrajetDao().addTrajet(trajet)
        bindingTrajetFragment.RecyclerTrajet.layoutManager = GridLayoutManager(requireActivity(),1)
       // bindingTrajetFragment.RecyclerTrajet.adapter = data.let { TrajetAdapter(requireActivity(), it) }
        //Toast.makeText(requireActivity(),trajet2.toString(), Toast.LENGTH_SHORT).show()
        //val data = mutableListOf<Trajet>()
        //data.add(Trajet(1,"Dimanche","16:00","17:00",200))
        //val view = requireActivity().findViewById<RecyclerView>(R.id.RecyclerTrajet)
        //val layoutManager = LinearLayoutManager(requireContext())
        //view.layoutManager = layoutManager
        //view.adapter = TrajetAdapter(requireContext(), data)

    }

}