package com.commonsware.todo.report

import android.content.Context
import android.net.Uri
import com.commonsware.todo.R
import com.commonsware.todo.repo.ToDoModel
import com.github.jknack.handlebars.Handlebars
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.OutputStreamWriter

class RosterReport(private val context: Context, engine: Handlebars) {
    private val template =
        engine.compileInline(context.getString(R.string.report_template))

    suspend fun generate(content: List<ToDoModel>, doc: Uri) {
        withContext(Dispatchers.IO) {
            OutputStreamWriter(context.contentResolver.openOutputStream(doc)).use { osw ->
                osw.write(template.apply(content))
                osw.flush()
            }
        }
    }
}
