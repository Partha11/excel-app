package com.tiptoptips.xl.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import com.tiptoptips.xl.R;
import com.tiptoptips.xl.listener.OnItemClickedListener;
import com.tiptoptips.xl.model.UserFile;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FilesAdapter extends RecyclerView.Adapter<FilesAdapter.ViewHolder> {

    private List<UserFile> userFiles;
    private Context context;
    private OnItemClickedListener listener;

    public FilesAdapter(Context context, List<UserFile> userFiles) {

        this.context = context;
        this.userFiles = userFiles;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.model_file, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.fileTitle.setText(userFiles.get(position).getFileName());
    }

    @Override
    public int getItemCount() {

        return userFiles == null ? 0 : userFiles.size();
    }

    public void setFileList(List<UserFile> userFileList) {

        this.userFiles = userFileList;
        notifyDataSetChanged();
    }

    public void setListener(OnItemClickedListener listener) {

        this.listener = listener;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.note_root_layout)
        LinearLayout noteRootLayout;
        @BindView(R.id.file_title)
        AppCompatTextView fileTitle;
        @BindView(R.id.add_note_text)
        AppCompatTextView addNoteText;

        ViewHolder(@NonNull View itemView) {

            super(itemView);
            ButterKnife.bind(this, itemView);

            addNoteText.setText("Add Note");
        }

        @OnClick(R.id.note_root_layout)
        public void onViewClicked() {

            listener.onItemClick(userFiles.get(getAdapterPosition()).getId());
        }
    }
}
