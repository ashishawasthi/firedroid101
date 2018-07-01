package app.popq.v01.popqv01

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.AdapterView
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import android.widget.ListView

import kotlinx.android.synthetic.main.activity_main.*
import android.widget.TextView
import android.widget.ArrayAdapter
import androidx.core.app.ActivityCompat
import android.content.Intent



class MainActivity : AppCompatActivity(), ActivityCompat.OnRequestPermissionsResultCallback, AdapterView.OnItemClickListener {
    private val _tag = "ChooserActivity"
    private val _classes = arrayOf<Class<*>>(LivePreviewActivity::class.java, StillImageActivity::class.java)
    private val _description_ids = intArrayOf(R.string.desc_camera_source_activity, R.string.desc_still_image_activity)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(_tag, "onCreate")
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        val listView = findViewById<ListView>(R.id.main_activity_list)
        val adapter = MyArrayAdapter(this, android.R.layout.simple_list_item_2, _classes)
        adapter.setDescriptionIds(_description_ids)
        listView.adapter = adapter
        listView.setOnItemClickListener(this)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "No Action defined", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onItemClick(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        val clicked = _classes[position]
        startActivity(Intent(this, clicked))
    }

    private class MyArrayAdapter(context: Context, resource: Int, private val classes: Array<Class<*>>) : ArrayAdapter<Class<*>>(context, resource, classes) {
        private var descriptionIds: IntArray? = null

        override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
            var view = convertView

            if (convertView == null) {
                val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
                view = inflater.inflate(android.R.layout.simple_list_item_2, null)
            }

            (view!!.findViewById(android.R.id.text1) as TextView).text = classes[position].simpleName
            (view!!.findViewById(android.R.id.text2) as TextView).setText(descriptionIds!![position])

            return view
        }

        fun setDescriptionIds(descriptionIds: IntArray) {
            this.descriptionIds = descriptionIds
        }
    }
}
