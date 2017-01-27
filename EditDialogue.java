package codepathtodoapp.myandroid.com.todoapp;

/**
 * Created by jsaluja on 1/11/2017.
 */

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import java.util.Calendar;

public class EditDialogue extends DialogFragment {
    private EditText EtSubmitText;//, EtSubmitPriority;
    private Button BtnSave;
    private String ClickedText = "clicked_text_string";
    private String ClickedPriority = "clicked_priority";
    private DatePicker datePicker;
    private Spinner PriDropdown;
    private String PrioritySelected;
    private int PriorityPosSelected;


    public interface EditDialogueListener {
        void onFinishEditDialog(String inputText, String inputDate, int inputPriority);
    }

    EditDialogueListener EditDialogueListener;

    public EditDialogue() {
    }

    public static EditDialogue newInstance(String title, int Priority) {
        EditDialogue frag = new EditDialogue();
        Bundle args = new Bundle();
        args.putString("clicked_text_string", title );
        args.putInt("clicked_priority", Priority );
        frag.setArguments(args);
        return frag;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            EditDialogueListener = (EditDialogueListener)activity;
        }
        catch(Exception ex){}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        View view = inflater.inflate(R.layout.activity_edit_item, container);
        Calendar c = Calendar.getInstance();

        EtSubmitText = (EditText)view.findViewById(R.id.ScndActEditTextId);
       // EtSubmitPriority = (EditText)view.findViewById(R.id.ScndActPriorityEditTextId);
        BtnSave = (Button)view.findViewById(R.id.ScndActButtonId);
        this.datePicker = (DatePicker) view.findViewById(R.id.ScndActDateId);
        this.datePicker.init(c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DAY_OF_MONTH), null);

        this.setCancelable(false);

        PriDropdown = (Spinner)view.findViewById(R.id.ScndActSpinnerId);
        //pri_dropdown.setOnItemSelectedListener(new CustomOnItemSelectedListener());
        PriDropdown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                PrioritySelected = parentView.getItemAtPosition(position).toString();
                PriorityPosSelected = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {

            }
        });

        Bundle bundle = getArguments();
        EtSubmitText.setText(bundle.getString(ClickedText));
        PriDropdown.setSelection(bundle.getInt(ClickedPriority));

        //Place cursor at the end of the displayed value
        EtSubmitText.setSelection(EtSubmitText.getText().length());
        //Make sure the main text is focused
        EtSubmitText.requestFocus();

        BtnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int datePickerMonth = 1 + EditDialogue.this.datePicker.getMonth();
                String date_string = EditDialogue.this.datePicker.getYear() + "/" + datePickerMonth + "/" + EditDialogue.this.datePicker.getDayOfMonth();

                    EditDialogueListener listener = (EditDialogueListener) getActivity();
                    listener.onFinishEditDialog(EtSubmitText.getText().toString(), date_string, PriorityPosSelected);
                    dismiss();
            }
        });
        return view;
    }
}
