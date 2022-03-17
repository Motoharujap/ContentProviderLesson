package com.swtecnn.contentproviderlesson

import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.swtecnn.contentproviderlesson.databinding.FragmentAudioListBinding

/**
 * A simple [Fragment] subclass.
 * Use the [AudioListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AudioListFragment : Fragment() {
    private var _binding: FragmentAudioListBinding? = null
    private val binding: FragmentAudioListBinding get() = _binding!!
    private lateinit var contactsAdapter: ContactsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAudioListBinding.inflate(inflater, container, false)
        contactsAdapter = ContactsAdapter()
        val layoutManager = LinearLayoutManager(activity?.applicationContext)
        binding.contactsRecyclerView.layoutManager = layoutManager
        binding.contactsRecyclerView.adapter = contactsAdapter
        fillAdapter()
        return binding.root
    }

    private fun fillAdapter() {
        val projection = arrayOf(
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media.DATE_TAKEN
        )
        val cursor = activity?.contentResolver?.query(
            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, null, null,
            null, null
        )
        val audioList = ArrayList<ContactItem>()
        cursor?.use {
            while(it.moveToNext()){
                val audioName = cursor.getString(cursor.getColumnIndex(MediaStore.Audio.AudioColumns.DISPLAY_NAME))
                audioList.add(ContactItem(audioName))
            }
        }
        contactsAdapter.setItems(audioList)
        cursor?.close()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = AudioListFragment()
    }
}