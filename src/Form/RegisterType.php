<?php

namespace App\Form;

use App\Entity\Account;
use Symfony\Component\Form\AbstractType;
use Symfony\Component\Form\Extension\Core\Type\CheckboxType;
use Symfony\Component\Form\Extension\Core\Type\ChoiceType;
use Symfony\Component\Form\Extension\Core\Type\HiddenType;
use Symfony\Component\Form\Extension\Core\Type\PasswordType;
use Symfony\Component\Form\Extension\Core\Type\TextType;
use Symfony\Component\Form\FormBuilderInterface;
use Symfony\Component\OptionsResolver\OptionsResolver;
use Symfony\Component\Security\Core\Encoder\UserPasswordEncoderInterface;
use Symfony\Component\Validator\Constraints as Assert;

class RegisterType extends AbstractType
{
    private $passwordEncoder;

    public function __construct(UserPasswordEncoderInterface $passwordEncoder)
    {
        $this->passwordEncoder = $passwordEncoder;
    }

    public function buildForm(FormBuilderInterface $builder, array $options)
    {
        $builder
            ->add('Name', TextType::class)
            ->add('Surname', TextType::class)
            ->add('Gender', ChoiceType::class, [
                'choices' => [
                    'Select gender' => null,
                    'Male' => 'male',
                    'Female' => 'female',
                ],
                'attr' => [
                    'class' => 'form-control',
                ],
            ])
            ->add('Phone_Number', TextType::class)
            ->add('Address', TextType::class)
            ->add('Email', TextType::class);

        // Add password field only if include_password_field option is true
        if ($options['include_password_field']) {
            $builder->add('Password', PasswordType::class);
        }

        // Add role field only if include_role_field option is true
        if ($options['include_role_field']) {
            $builder
                ->add('Role', ChoiceType::class, [
                    'choices' => [
                        'Select role' => null,
                        'User' => 'user',
                        'Admin' => 'admin',
                        'Veterinary' => 'veterinary',
                    ],
                    'attr' => [
                        'class' => 'form-control',
                    ],
                ]);
        }

        // Add account status field if it's an update form
        if ($options['is_update_form'] && $options['include_account_status_field']) {
            $builder->add('Account_Status', ChoiceType::class, [
                'choices' => [
                    'Select Status' => '',
                    'Pending' => 'pending',
                    'Active' => 'active',
                    'Blocked' => 'blocked',
                ],
                'attr' => [
                    'class' => 'form-control',
                ],
                'constraints' => [
                    new Assert\NotBlank([
                        'message' => 'Status cannot be empty',
                    ]),
                ],
            ]);
        }
    }

    public function configureOptions(OptionsResolver $resolver)
    {
        $resolver->setDefaults([
            'data_class' => Account::class,
            'include_password_field' => true, // Default to including password field
            'include_role_field' => true, // Default to including role field
            'include_account_status_field' => true, // Default to including account status field
            'is_update_form' => false, // Default to not being an update form
        ]);
    }
}
